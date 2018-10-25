package com.dmtest.netty_learn.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

/**
 * Listing 11.6 Init the ChannelPipeline with encryption
 * 2018/10/25.
 */
public class SecureChatServerIntializer11_6 extends ChatServerInitializer11_3 {

    private final SSLContext context;

    public SecureChatServerIntializer11_6(ChannelGroup group, SSLContext context){
        super(group);
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine engine = context.createSSLEngine();
        engine.setUseClientMode(false);
        ch.pipeline().addFirst(new SslHandler(engine));
    }

}
