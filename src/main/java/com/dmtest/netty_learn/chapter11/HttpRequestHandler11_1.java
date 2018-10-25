package com.dmtest.netty_learn.chapter11;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.RandomAccessFile;

/**
 * Created by dimi on 2018/10/24.
 */
public class HttpRequestHandler11_1 extends SimpleChannelInboundHandler<FullHttpRequest> { // #1

    private final String wsUri;

    public HttpRequestHandler11_1(String wsUri){
        this.wsUri = wsUri;
    }


    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception{
        if(wsUri.equalsIgnoreCase(req.getUri())) {
            ctx.fireChannelRead(req.retain());                       //#2
        } else {
            if(HttpHeaders.is100ContinueExpected(req)) {
                send100Continue(ctx);                               // #3
            }
            System.out.println(" ----> 展示 index.html");
            //RandomAccessFile file = new RandomAccessFile("D:\\oschinagit\\netty_learn\\src\\main\\java\\com\\dmtest\\netty_learn\\chapter11\\index.html","r");    // #4
            RandomAccessFile file = new RandomAccessFile("/Users/deming/IdeaProjects/project_one/netty_learn/src/main/java/com/dmtest/netty_learn/chapter11/index.html","r");    // #4
            HttpResponse response = new DefaultFullHttpResponse(req.getProtocolVersion(),HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain; charset=UTF-8");

            boolean keepAlive = HttpHeaders.isKeepAlive(req);

            if(keepAlive) {             //  #5
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set( HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);            // #6

            if (ctx.pipeline().get(SslHandler.class) == null){          // #7
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }

            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);   // #8
            if(!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);                            // #9
            }
        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


//    #1 继承 SimpleChannelInboundHandler 处理 FullHttpRequest 消息
//    #2 检查请求是否是一个 websocket 升级请求，如果是，retain it 并将其传递到 ChannelPipeline 中的下一个 ChannelInboundHandler。
//    #3 Handle 100 Continue requests to conform HTTP 1.1
//    #4 打开需要写向客户端的 index.html 文件。
//    #5 根据是否使用了 keepalive 来添加需要的头信息。
//    #6 将 HttpRequest 消息写向客户端。注意，使用的是 HttpRequest 而不是 FullHttpRequest。因为它仅是请求的第一部分。
//       同样的，我们没有使用 writeAndFlush(..) ，因为这个稍后会做。
//    #7 将 index.html 写向客户端。根据是否使用 SslHandler 来决定使用 DefaultFileRegion or ChunkedNioFile
//    #8 Write and flush the LastHttpContent to the client which marks the requests as complete.
//    #9 Depending on if keepalive is used close the Channel after the write completes.

}
