package com.dmtest.netty_learn.chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * Listing 9.7 IllegalStateException thrown due to invalid configuration
 * Created by dimi on 2018/10/23.
 */
public class InvalidConf9_7 {

    public void server (){
        final AttributeKey<Integer> id = AttributeKey.valueOf("ID");

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx)throws Exception {
                        Integer idValue = ctx.channel().attr(id).get();
                        // do something
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
                        System.out.println(" Received data ");
                        byteBuf.clear();
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr(id,123456);
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("www.maning.com",80));
        future.syncUninterruptibly();

    }

}
