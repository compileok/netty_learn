package com.dmtest.netty_learn.heart_beat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

            //标准输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            //利用死循环，不断读取客户端在控制台上的输入内容
            for (;;){
                channel.writeAndFlush(bufferedReader.readLine() +"\r\n");
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

        }
    }

}
