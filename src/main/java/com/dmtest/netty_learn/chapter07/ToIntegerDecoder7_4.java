package com.dmtest.netty_learn.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * 2018/9/29.
 */
public class ToIntegerDecoder7_4 extends ReplayingDecoder<Void>{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(byteBuf.readInt());
        //Read integer from inbound ByteBuf and add it to the List of decoded messages
    }
}
