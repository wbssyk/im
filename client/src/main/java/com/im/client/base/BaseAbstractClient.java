package com.im.client.base;

import com.google.protobuf.Timestamp;
import com.im.entity.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import com.im.client.handler.ClientHandler;
import com.im.code.TypeData;
import com.im.listener.MsgListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author shiyakun
 */
public abstract class BaseAbstractClient {
    private NioEventLoopGroup worker = new NioEventLoopGroup();

    private Channel channel;

    private Bootstrap bootstrap;

    private Integer port;

    private String host;

    public BaseAbstractClient() {
        ServerProp hostAndPort = getHostAndPort();
        this.host = hostAndPort.getHost();
        this.port = hostAndPort.getPort();
    }

    public void start() {
        startWorker();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void startWorker() {
        bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        // TODO Auto-generated method stub
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 0, 5));
                        pipeline.addLast(new ProtobufVarint32FrameDecoder());
                        pipeline.addLast(new ProtobufDecoder(Ack.AckMsg.getDefaultInstance()));
                        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                        pipeline.addLast(new ProtobufEncoder());
                        pipeline.addLast(new ClientHandler(BaseAbstractClient.this, getListener(), host, port));
                    }
                });
        doConnect(host, port);
    }

    /**
     * 连接服务端 and 重连
     */
    public void doConnect(String host, Integer port) {
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture connect = bootstrap.connect(host, port);
        //实现监听通道连接的方法
        connect.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    channel = connect.channel();
                    System.out.println("连接状态  通道---------" + BaseAbstractClient.this.channel.isActive());
                    System.out.println("连接成功");
                } else {
                    System.out.println("每隔2s重连....");
                    channelFuture.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            doConnect(host, port);
                        }
                    }, 2, TimeUnit.SECONDS);
                }
            }
        });
    }


    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(Message message) {
        if (channel != null && channel.isActive()) {
            Ack.AckMsg.Builder builder = Ack.AckMsg.newBuilder();
            builder.setType(TypeData.MESSAGE);
            builder.setToId(message.getToId());
            builder.setFromId(message.getFromId());
            builder.setBody(message.getBody());
            builder.setDate(Timestamp.newBuilder().setSeconds(System.currentTimeMillis()).build());
            channel.writeAndFlush(builder.build());
        }
    }

    /**
     * 注册用户
     *
     * @param register
     */
    public void register(Register register) {
        if (channel != null && channel.isActive()) {
            Ack.AckMsg.Builder builder = Ack.AckMsg.newBuilder();
            builder.setType(TypeData.ACK);
            builder.setFromId(register.getUserId());
            channel.writeAndFlush(builder.build());
        }
    }

    /**
     * 关闭连接
     */
    public void disConnect() {
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    /**
     * 获取listener
     *
     * @return
     */
    public abstract MsgListener getListener();

    /**
     * 获取listener
     *
     * @return
     */
    public abstract ServerProp getHostAndPort();
}
