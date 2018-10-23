package com.dmtest.netty_learn.chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/**
 *
 * Listing 9.8 Using Bootstrap with DatagramChannel
 * 2018/10/23.
 */
public class DatagramChannel9_8 {

    public void server(){

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new OioEventLoopGroup())
                .channel(OioDatagramChannel.class)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        // Do something with the packet
                    }
                });

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(0));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if(f.isSuccess()) {
                    System.out.println(" Channel bound ");
                }else {
                    System.err.println(" Bound attempt failed ");
                    future.cause().printStackTrace();
                }
            }
        });

    }

}
