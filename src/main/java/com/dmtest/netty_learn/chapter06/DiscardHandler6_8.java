package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 2018/9/29.
 */
@ChannelHandler.Sharable
public class DiscardHandler6_8 extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg){
        ReferenceCountUtil.release(msg);
    }

}
