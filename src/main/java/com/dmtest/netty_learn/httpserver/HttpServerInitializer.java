package com.dmtest.netty_learn.httpserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * server 初始化配置
 * 2018/11/1.
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new HttpServerCodec());// http 编解码
        pipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024)); // http 消息聚合器 512*1024为接收的最大contentlength
        pipeline.addLast(new HttpRequestHandler());

    }
}
