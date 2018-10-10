package com.dmtest.netty_learn.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 *  2018/10/9.
 */
public class ByteToCharDecoder7_11  extends ByteToMessageDecoder{
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 2) {
            out.add(Character.valueOf(in.readChar()));
        }
    }
}
