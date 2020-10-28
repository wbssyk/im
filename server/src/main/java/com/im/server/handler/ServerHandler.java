package com.im.server.handler;

import com.google.protobuf.Timestamp;
import com.im.entity.Ack;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import com.im.code.TypeData;
import com.im.entity.Model;
import com.im.handler.MiddlewareHandler;
import com.im.server.msg.ServerChannelMap;

import java.util.Date;

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
        Ack.AckMsg ack = (Ack.AckMsg) msg;
        long fromId = ack.getFromId();
        //首先判断是否已经注册成功
        if (ServerChannelMap.get(fromId) == null) {
            //todo 现在使用内存处理。后期升级为redis存储
            ServerChannelMap.add(fromId, ctx.channel());
            Ack.AckMsg.Builder builder = Ack.AckMsg.newBuilder();
            builder.setFromId(fromId);
            builder.setToId(ack.getToId());
            builder.setType(ack.getType());
            builder.setDate(ack.getDate());
            builder.setBody("注册成功");
            Ack.AckMsg build = builder.build();
            ctx.channel().writeAndFlush(build);
            System.out.println("用户: " + fromId + " :注册成功");
        } else {
            Ack.AckMsg.Builder builder = Ack.AckMsg.newBuilder();
            builder.setFromId(fromId);
            builder.setToId(ack.getToId());
            builder.setType(ack.getType());
            builder.setDate(ack.getDate());
            builder.setBody("请不要重复注册");
            Ack.AckMsg build = builder.build();
            ctx.channel().writeAndFlush(build);
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
        Ack.AckMsg ack = (Ack.AckMsg) msg;
        long toId = ack.getToId();
        long fromId = ack.getFromId();
        //说明是发送消息
        if (ServerChannelMap.isEmpty()) {
            //如果map为空说明没有任何channel注册
        }
        if (ServerChannelMap.get(toId) == null) {
            //对方不在线
            //todo 以后做离线消息处理，应该存储到 消息队列中 然后同步到数据库中,为下次用户启动展示历史数据
            Ack.AckMsg.Builder sendToClient = Ack.AckMsg.newBuilder();
            sendToClient.setBody("对方不在线");
            sendToClient.setType(TypeData.MESSAGE);
            sendToClient.setFromId(fromId);
            sendToClient.setToId(toId);
            Ack.AckMsg build = sendToClient.build();
            ctx.channel().writeAndFlush(build);
        }
        //此时就是对方在线,直接发送消息
        Channel channel = ServerChannelMap.get(toId);
        //把消息发给 toId的人
        channel.writeAndFlush(ack);

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
