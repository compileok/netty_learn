package com.dmtest.netty_learn.chapter08;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 *  2018/10/10.
 */
public class HttpsCodecInitializer8_5 extends ChannelInitializer<Channel> {

    private final SSLContext context;
    private final boolean client;

    public HttpsCodecInitializer8_5(SSLContext context,boolean client){
        this.context = context;
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(client);
        pipeline.addFirst("ssl",new SslHandler(engine));

        if(client) {
            pipeline.addLast("codec",new HttpClientCodec());
        }else {
            pipeline.addLast("codec",new HttpServerCodec());
        }
    }
}
