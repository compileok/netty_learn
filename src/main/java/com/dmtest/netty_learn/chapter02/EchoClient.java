package com.dmtest.netty_learn.chapter02;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by deming on 2018/9/8.
 */
public class EchoClient {

    private int port;
    private String host;

    EchoClient(int port,String host){
        this.port = port;
        this.host = host;
    }


    public void start() throws Exception{

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap boot = new Bootstrap();

        try{
            boot.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture future = boot.connect().sync();
            future.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully().sync();
        }
    }


    private class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
        // 这里使用了SimpleChannelInboundHandler 而不是 ChannelInboundHandlerAdapter
        // 区别在于 SimpleChannelInboundHandler会主动释放资源（本例中的ByteBuf in），而ChannelInboundHandlerAdapter不会。
        // 其实前者是继承自后者，然后复写了channelRead方法，并在channelRead方法中调用了channelRead0方法，然后释放资源（这些资源实现了ReferenceCounted接口）。
        // channelRead0方法其实是使用了模板方法设计模式。

        /*
         * You may ask yourself why we now used SimpleChannelInboundHandler and not ChannelInboundHandlerAdapter as it was used in the EchoServerHandler.
         * The main reason for this is that with ChannelInboundHandlerAdapter
         * you are responsible to  release  resources after you handled the received message.
         *
         */

        @Override
        public void channelActive(ChannelHandlerContext ctx){
            // this method will called once the connection is established
            ctx.write(Unpooled.copiedBuffer(" Netty : channel active ! ", CharsetUtil.UTF_8));
            ctx.flush();

        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
            // this method wil called once data is received form server.
            // 由于数据会被拆包，例如，传递5K的数据，并不能保证一次就接收了5K，可能拆成了3K,2K，所以这个方法会被调用两次。
            // 虽然不能保证5K的数据在一个包中传递过来，但能保证的是 the byte will be received in the same orders as they 're sent.
            // but this is only true for TCP or other stream-orientated protocols.
            System.out.println(" Client received : " + ByteBufUtil.hexDump(in.readBytes(in.readableBytes())));

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }


    }

    public static void main(String[] args) throws Exception{
        EchoClient client = new EchoClient(1234,"localhost");
        client.start();

    }

}
