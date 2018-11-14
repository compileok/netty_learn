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

    int port;
    Channel channel;
    Random random ;

    public HeartBeatClient(int port){
        this.port = port;
        random = new Random();
    }
    public static void main(String[] args) throws Exception{
        HeartBeatClient client = new HeartBeatClient(8090);
        client.start();
    }

    public void start() {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new HeartBeatClientInitializer());

            connect(bootstrap,port);
            String  text = "I am alive";
            while (channel.isActive()){
                sendMsg(text);
            }
        }catch(Exception e){
            // do something
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public void connect(Bootstrap bootstrap,int port) throws Exception{
        channel = bootstrap.connect("localhost",8090).sync().channel();
    }

    public void sendMsg(String text) throws Exception{
        channel.writeAndFlush(text);
        int num = random.nextInt(10);
        System.out.println(" client sent msg and sleep "+num);
        Thread.sleep(num * 1000);
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
