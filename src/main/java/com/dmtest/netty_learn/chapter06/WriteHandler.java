package com.dmtest.netty_learn.chapter06;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 *
 * 2018/9/28.
 */
public class WriteHandler extends ChannelHandlerAdapter {

    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        this.ctx = ctx; // Store reference to ChannelHandlerContext for later use
    }

    public void send(String msg){
        ctx.write(msg); // Send message using previously stored ChannelHandlerContext
    }

}
