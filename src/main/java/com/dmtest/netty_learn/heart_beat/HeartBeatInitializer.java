package com.dmtest.netty_learn.heart_beat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by dimi on 2018/11/13.
 */
public class HeartBeatInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast(new IdleStateHandler(10,0,0, TimeUnit.SECONDS));
        pipeline.addLast(new HeartBeatHandler());
    }
}
