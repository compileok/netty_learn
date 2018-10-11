package com.dmtest.netty_learn.chapter08;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 *  2018/10/11.
 */
public class ProtoBufInitializer8_16 extends ChannelInitializer<Channel>{

    private final MessageLite lite;

    public ProtoBufInitializer8_16(MessageLite lite){
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(lite));
        pipeline.addLast(new ObjectHandler());
    }

    public static final class ObjectHandler extends SimpleChannelInboundHandler<Object>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
            // do something
        }
    }
}
