package com.dmtest.netty_learn.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Listing 10.3 AbsIntegerEncoder
 * 2018/10/24.
 */
public class AbsIntegerEncoder10_3 extends MessageToMessageEncoder<ByteBuf>{

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >=4 ) {
            int value = Math.abs(in.readInt());
            out.add(value);
        }
    }
}
