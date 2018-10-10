package com.dmtest.netty_learn.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by dimi on 2018/10/9.
 */
public class IntegerToByteEncoder7_8 extends MessageToByteEncoder<Short>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Short msg, ByteBuf out) throws Exception {
        out.writeShort(msg);
    }
}
