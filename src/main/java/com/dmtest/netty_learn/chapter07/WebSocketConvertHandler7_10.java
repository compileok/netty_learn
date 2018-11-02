package com.dmtest.netty_learn.chapter07;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * 2018/10/9.
 */
@ChannelHandler.Sharable
public class WebSocketConvertHandler7_10 extends MessageToMessageCodec<WebSocketFrame,WebSocketConvertHandler7_10.WebSocketFrame2> {

    private WebSocketConvertHandler7_10 INSTANCE = new WebSocketConvertHandler7_10();

    @Override
    protected void encode(ChannelHandlerContext ctx, WebSocketFrame2 msg, List<Object> out) throws Exception {
        switch (msg.getType()) {
            case BINARY:
                out.add(new BinaryWebSocketFrame(msg.getData()));
                return;
            case TEXT:
                out.add(new TextWebSocketFrame(msg.getData()));
                return;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true,0,msg.getData()));
                return;
            case PONG:
                out.add(new PongWebSocketFrame(msg.getData()));
                return;
            case PING:
                out.add(new PingWebSocketFrame(msg.getData()));
                return;
            default:
                throw new IllegalStateException(
                        "Unsupported websocket msg " + msg);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (msg instanceof BinaryWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.BINARY, msg.content().copy()));
            return;
        }

        if (msg instanceof CloseWebSocketFrame) {
            out.add(new WebSocketFrame2(WebSocketFrame2.FrameType.CLOSE, msg.content().copy()));
            return;
        }

        if (msg instanceof PingWebSocketFrame) {
            out.add(new WebSocketFrame2(
                    WebSocketFrame2.FrameType.PING, msg.content().copy()));
            return;
        }
        if (msg instanceof PongWebSocketFrame) {
            out.add(new WebSocketFrame2(
                    WebSocketFrame2.FrameType.PONG, msg.content().copy()));
            return;
        }
        if (msg instanceof TextWebSocketFrame) {
            out.add(new WebSocketFrame2(
                    WebSocketFrame2.FrameType.TEXT, msg.content().copy()));
            return;
        }
        if (msg instanceof ContinuationWebSocketFrame) {
            out.add(new WebSocketFrame2(
                    WebSocketFrame2.FrameType.CONTINUATION,
                    msg.content().copy()));
            return;
        }
        throw new IllegalStateException("Unsupported websocket msg " + msg);
    }

    public static final class WebSocketFrame2 {
        public enum FrameType {
            BINARY,
            CLOSE,
            PING,
            PONG,
            TEXT,
            CONTINUATION
        }
        private final FrameType type;
        private final ByteBuf data;
        public WebSocketFrame2(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }
        public FrameType getType() {
            return type;
        }
        public ByteBuf getData() {
            return data;
        }
    }

}
