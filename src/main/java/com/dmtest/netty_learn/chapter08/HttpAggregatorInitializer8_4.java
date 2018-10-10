package com.dmtest.netty_learn.chapter08;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 2018/10/10.
 */
public class HttpAggregatorInitializer8_4 extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer8_4(boolean client){
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        if(client) {
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("decompressor",new HttpContentDecompressor());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
            pipeline.addLast("compressor",new HttpContentCompressor());
        }
    }
}
