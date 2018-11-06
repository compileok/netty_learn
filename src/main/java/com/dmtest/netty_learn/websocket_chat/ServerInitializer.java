package com.dmtest.netty_learn.websocket_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 *  2018/11/5.
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
    }
}
