package com.dmtest.netty_learn.websocket_chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import java.net.InetSocketAddress;

/**
 * websocket server
 * 2018/11/5.
 */
public class WebsocketServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    int port;
    public WebsocketServer(int port){
        this.port = port;
    }

    public ChannelFuture start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer());
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port));
        future.syncUninterruptibly();
        return future;
    }
    public void destroy(){
        channelGroup.close();
        group.shutdownGracefully();
    }

}
