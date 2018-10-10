package com.dmtest.netty_learn.chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 2018/10/10.
 */
public class LineBasedHandlerInitializer8_10 extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LineBasedFrameDecoder(65*1024));
        pipeline.addLast(new FrameHandler());
    }

    public static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
                throws Exception {
            // do something with the frame
        }
    }
}
