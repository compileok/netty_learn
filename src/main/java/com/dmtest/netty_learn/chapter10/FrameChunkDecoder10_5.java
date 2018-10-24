package com.dmtest.netty_learn.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Listing 10.5 FrameChunkDecoder
 * 2018/10/24.
 */
public class FrameChunkDecoder10_5 extends ByteToMessageDecoder{

    private final int maxFrameSize;

    public FrameChunkDecoder10_5(int maxFrameSize){
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes();
        if(readableBytes > maxFrameSize) {
            in.clear();
            throw new TooLongFrameException();
        }
        ByteBuf buf = in.readBytes(readableBytes);
        out.add(buf);
    }
}
