package com.dmtest.netty_learn.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 *
 * Listing 10.1 FixedLengthFrameDecoder implementation
 * 2018/10/23.
 */
public class FixedLengthFrameDecoder10_1 extends ByteToMessageDecoder{

    private final int frameLength;

    private FixedLengthFrameDecoder10_1(int frameLength){
        if(frameLength <= 0 ) {
            throw new IllegalArgumentException(" frameLength must be a positive integer:" + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= frameLength) {
            ByteBuf buf = in.readBytes(frameLength);
            out.add(buf);
        }
    }
}
