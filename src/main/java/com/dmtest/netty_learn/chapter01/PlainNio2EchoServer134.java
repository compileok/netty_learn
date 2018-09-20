package com.dmtest.netty_learn.chapter01;

import com.dmtest.netty_learn.utils.CloseUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * Created by deming on 2018/9/2.
 */
public class PlainNio2EchoServer134 {


    public void serve(int port) throws Exception {

        System.out.println("Listening for connections on port " + port);

        final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);

        serverChannel.bind(address);

        final CountDownLatch latch = new CountDownLatch(1);
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

            @Override
            public void completed(AsynchronousSocketChannel channel, Object attachment) {
                serverChannel.accept(null,this);
                ByteBuffer buffer = ByteBuffer.allocate(100);
                channel.read(buffer,buffer,new EchoComletionHandler(channel));

            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                CloseUtil.close(serverChannel);
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }


    private final class EchoComletionHandler implements CompletionHandler<Integer,ByteBuffer> {


        private final AsynchronousSocketChannel channel;

        EchoComletionHandler(AsynchronousSocketChannel channel) {
            this.channel = channel;
        }
        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            buffer.flip();

            channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    if(buffer.hasRemaining()) {
                        channel.write(buffer,buffer,this);
                    } else {
                        buffer.compact();
                        channel.read(buffer,buffer,EchoComletionHandler.this);
                    }
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    CloseUtil.close(channel);
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
            CloseUtil.close(channel);
        }
    }


}
