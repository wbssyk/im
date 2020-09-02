package com.example.server;

import com.example.server.handler.ServerHandler;
import com.example.util.BannerHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import com.example.code.MsgPckDecode;
import com.example.code.MsgPckEncode;
import com.example.util.PropUtils;

/**
 * @author shiyakun
 */
public class Server {
    public static void main(String[] args) {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(PropUtils.getProp().getHost(),PropUtils.getProp().getPort())
                    .childHandler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            // TODO Auto-generated method stub
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(10, 0, 0));
                            pipeline.addLast(new MsgPckDecode());
                            pipeline.addLast(new MsgPckEncode());
                            pipeline.addLast(new ServerHandler());
                        }
                    });
            BannerHelper.banner(0);
            ChannelFuture sync = serverBootstrap.bind().sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //优雅的关闭资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

