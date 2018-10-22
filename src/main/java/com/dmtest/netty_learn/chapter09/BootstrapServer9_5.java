package com.dmtest.netty_learn.chapter09;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by dimi on 2018/10/22.
 */
public class BootstrapServer9_5 {

    public void server(){

        ServerBootstrap boot = new ServerBootstrap(); // #1
        boot.group(new NioEventLoopGroup(), new NioEventLoopGroup())  //#2
                .channel(NioServerSocketChannel.class) //#3
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx)
                            throws Exception {
                        Bootstrap bootstrap = new Bootstrap(); //#5
                        bootstrap.channel(NioSocketChannel.class) //#6
                                .handler(new SimpleChannelInboundHandler<ByteBuf>(){ // #7
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx,ByteBuf in){
                                        System.out.println("Reveived data");
                                        in.clear();
                                    }
                                });
                        bootstrap.group(ctx.channel().eventLoop()); // #8
                        connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com",80)); // #9
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx,ByteBuf in) throws Exception {
                       if(connectFuture.isDone()) {
                           // do something with the data                    // #10
                       }
                    }
                });
        ChannelFuture future = boot.bind(new InetSocketAddress(8080));   //  #11
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    System.out.println(" Server Bound . ");
                }else {
                    System.out.println(" Server attempt failed ");
                    channelFuture.cause().printStackTrace();
                }
            }
        });


//        #1 Create a new ServerBootstrap to create new SocketChannel channels and bind them
//        #2 Specify the EventLoopGroups to get EventLoops from and register with the ServerChannel and the
//        accepted channels
//        #3 Specify the channel class which will be used to instance
//        #4 Set a handler which will handle I/O and data for the accepted channels
//        #5 Create a new bootstrap to connect to remote host
//        #6 Set the channel class
//        #7 Set a handler to handle I/O
//        #8 Use the same EventLoop as the one thats assigned to the accepted channel to minimize context-switching and so on
//        #9 Connect to remote peer
//        #10 Do something with the data if the connect is complete, for example, proxy it
//        #11 Bind the channel as its connectionless with the configured bootstrap

    }
}
