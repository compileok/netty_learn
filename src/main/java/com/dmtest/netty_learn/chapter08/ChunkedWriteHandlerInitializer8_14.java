package com.dmtest.netty_learn.chapter08;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/**
 * 2018/10/10.
 */
public class ChunkedWriteHandlerInitializer8_14 extends ChannelInitializer<Channel> {

    private final File file;

    public ChunkedWriteHandlerInitializer8_14(File file){
        this.file = file;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new WriteStreamHandler());

    }


    public  final class WriteStreamHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelActive(ChannelHandlerContext ctx)throws Exception{
            super.channelActive(ctx);
            ctx.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }

}
