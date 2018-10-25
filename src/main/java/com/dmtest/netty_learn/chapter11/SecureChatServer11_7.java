package com.dmtest.netty_learn.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;

/**
 * Listing 11.7 Init the ChannelPipeline with encryption
 * 2018/10/25.
 */
public class SecureChatServer11_7 extends ChatServer11_4{

    private final SSLContext context;

    public SecureChatServer11_7(SSLContext context) {
        this.context = context;
    }
    @Override
    protected ChannelInitializer<Channel> createInitializer( ChannelGroup group) {
        return new SecureChatServerIntializer11_6(group, context);
    }

    public static void main(String[] args) {

        int port = 8090;

//        SSLContext context = BogusSslContextFactory.getServerContext();
        SSLContext context = null; // FIXME 这里需要修改
        final SecureChatServer11_7 endpoint = new SecureChatServer11_7(context);
        ChannelFuture future = endpoint.start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                endpoint.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
