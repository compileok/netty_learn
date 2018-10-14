package com.dmtest.netty_learn.chapter08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;


/**
 * 2018/10/10.
 */
public class LengthBasedInitializer8_12 extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new LengthFieldBasedFrameDecoder(65 * 1024 , 0 ,8));
        pipeline.addLast(new FrameHandler());

    }

    public static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            ctx.write(msg);
        }
    }

}
