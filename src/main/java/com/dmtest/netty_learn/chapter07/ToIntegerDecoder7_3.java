package com.dmtest.netty_learn.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 2018/9/29.
 */
public class ToIntegerDecoder7_3 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() >= 4) {//Check if there are at least 4 bytes readable
            list.add(byteBuf.readInt()); //Read integer from inbound ByteBuf, add to the List of decodec messages
        }
    }
}
