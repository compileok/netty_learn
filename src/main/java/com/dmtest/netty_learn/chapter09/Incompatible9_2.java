package com.dmtest.netty_learn.chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by deming on 2018/10/14.
 */
public class Incompatible9_2 {

    private void incompatibleSample(){

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(OioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(
                            ChannelHandlerContext channelHandlerContext,
                            ByteBuf byteBuf) throws Exception {
                        System.out.println("Reveived data");
                        byteBuf.clear();
                    }
                });
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();

    }

}
