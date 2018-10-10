package com.dmtest.netty_learn.chapter08;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *  2018/10/9.
 */
public class HttpAggregatorInitializer8_3 extends ChannelInitializer<Channel>{

    private final boolean client;
    public HttpAggregatorInitializer8_3(boolean client){
        this.client = client;
    }



    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        if(client) {
            pipeline.addLast("codec",new HttpClientCodec());
        } else {
            pipeline.addLast("codec",new HttpServerCodec());
        }

        pipeline.addLast("aggegator",new HttpObjectAggregator(512*1024));
    }
}
