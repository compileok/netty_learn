package com.dmtest.netty_learn.chapter07;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

public class IntegerToStringEncoder7_9 extends MessageToMessageEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
