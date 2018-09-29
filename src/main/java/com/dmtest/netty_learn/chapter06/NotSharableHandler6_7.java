package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *  2018/9/28.
 */
@ChannelHandler.Sharable
public class NotSharableHandler6_7 extends ChannelInboundHandlerAdapter{

    private int count;

    public void channelRead(ChannelHandlerContext ctx,Object msg){
        count ++;
        System.out.println(" channelRead(...) called the " + count + " time. ");
        ctx.fireChannelRead(msg);
    }

}
