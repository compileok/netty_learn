package com.dmtest.netty_learn.websocket_chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 *  2018/11/5.
 */
public class ServerInitializer extends ChannelInitializer<Channel> {

    ChannelGroup channelGroup;

    public ServerInitializer(ChannelGroup channelGroup){
        this.channelGroup = channelGroup;
    }
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));

        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler(channelGroup));
    }
}
