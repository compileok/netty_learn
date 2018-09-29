package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *  2018/9/28.
 */
@ChannelHandler.Sharable
public class SharableHandler6_6 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) {
        System.out.println(" Channel read message " + msg);
        ctx.fireChannelRead(msg);
    }

}
