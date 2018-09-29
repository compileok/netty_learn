package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/**
 * 2018/9/29.
 */
public class DiscardOutboundHandler6_10 extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx,Object msg,ChannelPromise promise){
        ReferenceCountUtil.release(msg);
        promise.setSuccess(); // Notify ChannelPromise that data handled
    }

}
