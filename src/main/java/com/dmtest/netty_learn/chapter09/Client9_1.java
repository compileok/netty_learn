package com.dmtest.netty_learn.chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by deming on 2018/10/14.
 */
public class Client9_1 {


    private void createClient(){


        Bootstrap boot = new Bootstrap();
        boot.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new SimpleChannelInboundHandler<ByteBuf>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
                        System.out.println(" Reveived data ");
                        byteBuf.clear();
                    }
                });

        ChannelFuture f = boot.connect(new InetSocketAddress("www.manning.com",80));
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()) {
                    System.out.println(" connection established ");
                }else {
                    System.out.println(" connection attempt failed ");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

}
