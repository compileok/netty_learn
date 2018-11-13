package com.dmtest.netty_learn.heart_beat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 2018/11/13.
 */
public class HeartBeatHandler extends SimpleChannelInboundHandler<String> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println(" message received : " + s);
       if("I am alive".equals(s)){
            ctx.channel().writeAndFlush("copy that");
        }else {
           System.out.println(" 其他信息处理 ... ");
       }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event =(IdleStateEvent)evt;

        String eventType = null;
        switch (event.state()){
            case READER_IDLE:
                eventType = "读空闲";
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType ="读写空闲";
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "超时事件：" +eventType);
        ctx.channel().close();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("---" + ctx.channel().remoteAddress() + " is active---");
    }

}
