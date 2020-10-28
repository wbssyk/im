package com.im.client.handler;


import com.im.client.base.BaseAbstractClient;
import com.im.entity.Ack;
import com.im.entity.Message;
import com.im.listener.MsgListener;
import io.netty.channel.ChannelHandlerContext;
import com.im.handler.MiddlewareHandler;

import java.util.Date;

/**
 * @author shiyakun
 * @Description TODO
 */
public class ClientHandler extends MiddlewareHandler {
    private BaseAbstractClient baseAbstractClient;

    private MsgListener msgListener;

    private Integer port;

    private String host;

    public ClientHandler(BaseAbstractClient baseAbstractClient, MsgListener msgListener, String host, Integer port) {
        super("client");
        this.msgListener = msgListener;
        this.baseAbstractClient = baseAbstractClient;
        this.port = port;
        this.host = host;
    }

    @Override
    protected void handlerAck(ChannelHandlerContext ctx, Object msg) {
        Ack.AckMsg model = (Ack.AckMsg) msg;
        System.out.println("用户 " + model.getFromId() + " " + model.getBody());
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        Ack.AckMsg model = (Ack.AckMsg) msg;
        Message message = new Message();
        message.setBody(model.getBody());
        message.setFromId(model.getFromId());
        message.setToId(model.getToId());
        message.setType(model.getType());
        message.setDate(new Date(model.getDate().getSeconds()));
        //存储消息,消息发送给listener
        msgListener.receiveMsg(message);

    }

    @Override
    protected void handlerAllIdle(ChannelHandlerContext ctx) {
        super.handlerAllIdle(ctx);
        sendPingMsg(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        //todo 通知重连失败
//        //断线后把对应的ClientReceiveMsg清空
//        Integer fromId = ClientChannelMap.getFromId(ctx.channel());
//        ClientReceiveMsg.remove(fromId);
        baseAbstractClient.doConnect(host, port);
        //todo 通知重连成功
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(name + "exception :" + cause.toString());
    }


}
