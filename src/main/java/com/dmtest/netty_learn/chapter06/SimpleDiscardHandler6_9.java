package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 2018/9/29.
 */
public class SimpleDiscardHandler6_9 extends SimpleChannelInboundHandler<Object>{

    @Override
    public void channelRead0(ChannelHandlerContext ctx,Object msg){
        // no thing to do
    }

}
