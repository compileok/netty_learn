package com.dmtest.netty_learn.heart_beat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;

/**
 * Created by dimi on 2018/11/13.
 */
public class HeartBeatClient  {

    public static void main(String[] args) throws Exception{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new HeartBeatClientInitializer());

            Channel channel = bootstrap.connect("localhost",8090).sync().channel();

            String  text = "I am alive";

            Random random = new Random();
            while (channel.isActive()){
                int num = random.nextInt(10);
                System.out.println(num);
                Thread.sleep(num * 1000);
                channel.writeAndFlush(text);
            }

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }


    static class HeartBeatClientInitializer extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast(new HeartBeatClientHandler());

        }
    }


    static class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println(" client received :" +msg);
            if(msg!= null && msg.equals("you are out")) {
                System.out.println(" server closed connection , so client will close too");
                ctx.channel().closeFuture();
            }
        }


    }

}
