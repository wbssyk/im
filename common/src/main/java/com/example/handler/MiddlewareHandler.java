package com.example.handler;


import com.example.entity.Model;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import com.example.code.TypeData;

/**
 * @author shiyakun
 * @Description 公共Handler父类
 */
public abstract class MiddlewareHandler extends ChannelInboundHandlerAdapter {
    protected String name;
    /**
     * 心跳次数
     */
    private int heartbeatCount = 0;

    /**
     * 获取server and client 传入的值
     *
     * @param name
     */
    public MiddlewareHandler(String name) {
        this.name = name;
    }

    /**
     * 继承ChannelInboundHandlerAdapter实现了channelRead就会监听到通道里面的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        Model m = (Model) msg;
        int type = m.getType();
        switch (type) {
            case 0:
                handlerAck(ctx, msg);
                break;
            case 1:
                sendPongMsg(ctx);
                break;
            case 2:
//                System.out.println(name + " get  pong  msg  from" + ctx.channel().remoteAddress());
                break;
            case 3:
                handlerData(ctx, msg);
                break;
            default:
                break;
        }
    }

    /**
     * 处理第一次连接把用户信息注册
     *
     * @param ctx
     * @param msg
     */
    protected abstract void handlerAck(ChannelHandlerContext ctx, Object msg);


    /**
     * 处理接收到的聊天消息
     *
     * @param ctx
     * @param msg
     */
    protected abstract void handlerData(ChannelHandlerContext ctx, Object msg);


    /**
     * 发送ping消息
     * @param ctx
     */
    protected void sendPingMsg(ChannelHandlerContext ctx) {
        Model model = new Model();

        model.setType(TypeData.PING);

        ctx.channel().writeAndFlush(model);

        heartbeatCount++;

//        System.out.println(name + " send ping msg to " + ctx.channel().remoteAddress() + "count :" + heartbeatCount);
    }

    /**
     * 发送pong消息
     * @param ctx
     */
    private void sendPongMsg(ChannelHandlerContext ctx) {

        Model model = new Model();

        model.setType(TypeData.PONG);

        ctx.channel().writeAndFlush(model);

        heartbeatCount++;

//        System.out.println(name + " send pong msg to " + ctx.channel().remoteAddress() + " , count :" + heartbeatCount);
    }

    /**
     * 重写父类 心跳状态方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        IdleStateEvent stateEvent = (IdleStateEvent) evt;

        switch (stateEvent.state()) {
            case READER_IDLE:
                handlerReaderIdle(ctx);
                break;
            case WRITER_IDLE:
                handlerWriterIdle(ctx);
                break;
            case ALL_IDLE:
                handlerAllIdle(ctx);
                break;
            default:
                break;
        }
    }

    protected void handlerAllIdle(ChannelHandlerContext ctx) {
//        System.err.println("---ALL_IDLE---");
    }

    protected void handlerWriterIdle(ChannelHandlerContext ctx) {
//        System.err.println("---WRITER_IDLE---");
    }


    protected void handlerReaderIdle(ChannelHandlerContext ctx) {
//        System.err.println("---READER_IDLE---");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(" ---" + ctx.channel().remoteAddress() + "----- is  action");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println(" ---" + ctx.channel().remoteAddress() + "----- is  inAction");
    }
}
