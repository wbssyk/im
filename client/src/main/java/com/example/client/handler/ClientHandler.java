package com.example.client.handler;


import com.example.client.base.BaseAbstractClient;
import io.netty.channel.ChannelHandlerContext;
import com.example.entity.Model;
import com.example.handler.MiddlewareHandler;
import com.example.listener.MsgListener;

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
        Model model = (Model) msg;
        System.out.println("用户 " + model.getFromId() + " " + model.getBody());
    }

    @Override
    protected void handlerData(ChannelHandlerContext ctx, Object msg) {
        Model model = (Model) msg;
        //存储消息
//        ClientReceiveMsg.add(model.getFromId(), model);
//        ClientChannelMap.add(model.getFromId(), ctx.channel());
        msgListener.receiveMsg(model);

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
