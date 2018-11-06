package com.dmtest.netty_learn.websocket_chat;

import io.netty.channel.ChannelFuture;

/**
 * 2018/11/5.
 */
public class Application {


    public static void main(String[] args) {
        WebsocketServer server = new WebsocketServer(8090);
        ChannelFuture f = server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                server.destroy();
            }
        });
        f.channel().closeFuture().syncUninterruptibly();

    }



}
