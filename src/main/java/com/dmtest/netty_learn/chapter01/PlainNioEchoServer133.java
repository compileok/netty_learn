package com.dmtest.netty_learn.chapter01;

import com.dmtest.netty_learn.utils.CloseUtil;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * EchoServer based on NIO
 *
 * 2018/9/1.
 */
public class PlainNioEchoServer133 {

    public void serve(int port) throws Exception{
        System.out.println(" Listening for connection on port:" +port);

        ServerSocketChannel channel = ServerSocketChannel.open();

        ServerSocket ss = channel.socket();

        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);

        channel.configureBlocking(false);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try{
                selector.select();
            }catch (Exception e) {
                break;
            }
            Set readyKey = selector.selectedKeys();

            Iterator it = readyKey.iterator();

            while (it.hasNext()) {
                SelectionKey key = (SelectionKey) it.next();
                it.remove();

                try{

                    if(key.isAcceptable()){
                        ServerSocketChannel sever = (ServerSocketChannel)key.channel();
                        SocketChannel client = sever.accept();
                        System.out.println(" Accepted connection from :" + client);

                        client.configureBlocking(false);
                        client.register(selector,SelectionKey.OP_WRITE|SelectionKey.OP_READ, ByteBuffer.allocate(100));

                    }

                    if(key.isReadable()) {
                        SocketChannel client  = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        client.read(output);
                    }

                    if(key.isWritable()) {

                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();

                        output.flip();
                        client.write(output);
                        output.compact();
                    }

                }catch (Exception e) {
                    key.cancel();
                    CloseUtil.close(key.channel());
                }
            }
        }
    }
}
