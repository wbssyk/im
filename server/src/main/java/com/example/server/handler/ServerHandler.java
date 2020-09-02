package com.example.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import com.example.code.TypeData;
import com.example.entity.Model;
import com.example.handler.MiddlewareHandler;
import com.example.server.msg.ServerChannelMap;

/**
 * @author shiyakun
 * @Description TODO
 */
public class ServerHandler extends MiddlewareHandler {
    public ServerHandler() {
        super("server");
    }

    /**
     * 处理客户端用户id和channel的对应关系
     *
     * @param ctx
     * @param msg
     */
    @Override
    protected void handlerAck(ChannelHandlerContext ctx, Object msg) {
        Model model = (Model) msg;
        int fromId = model.getFromId();
        //首先判断是否已经注册成功
        if (ServerChannelMap.get(fromId) == null) {
            //todo 现在使用内存处理。后期升级为redis存储
            ServerChannelMap.add(fromId, ctx.channel());
            model.setBody("注册成功");
            ctx.channel().writeAndFlush(model);
            System.out.println("用户: " + fromId + " :注册成功");
        } else {
            model.setBody("请不要重复注册");
            ctx.channel().writeAndFlush(model);
            System.out.println("用户: " + fromId + " :请不要重复注册");
        }
    }

    /**
     * 处理正常消息
     * @param ctx
     * @param msg
     */
    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        Model model = (Model) msg;
        int toId = model.getToId();
        int fromId = model.getFromId();
        //说明是发送消息
        if (ServerChannelMap.isEmpty()) {
            //如果map为空说明没有任何channel注册
        }
        if (ServerChannelMap.get(toId) == null) {
            //对方不在线
            //todo 以后做离线消息处理，应该存储到 消息队列中 然后同步到数据库中,为下次用户启动展示历史数据
            Model sendToClient = new Model();
            sendToClient.setBody("对方不在线");
            sendToClient.setType(TypeData.MESSAGE);
            sendToClient.setFromId(fromId);
            sendToClient.setToId(toId);
            ctx.channel().writeAndFlush(sendToClient);
        }
        //此时就是对方在线,直接发送消息
        Channel channel = ServerChannelMap.get(toId);
        //把消息发给 toId的人
        channel.writeAndFlush(model);

    }

    /**
     * 关闭客户端
     * @param ctx
     */
    @Override
    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
        super.handlerReaderIdle(ctx);
        System.err.println(" ---- client " + ctx.channel().remoteAddress().toString() + " reader timeOut, --- close it");
        //客户端连接不上,就把客户端断开
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.err.println(name + "  exception " + cause.toString());

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //从map中移除channel
        ServerChannelMap.remove(ctx.channel());
    }
}
