package com.dmtest.netty_learn.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Listing 10.6 Testing FixedLengthFrameDecoder
 * 2018/10/24.
 */
public class FrameChunkDecoderTest10_6 {

    @Test
    public void testFramesDecoded(){

        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buf.writeByte(i);
        }
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder10_5(3));

        Assert.assertTrue(channel.writeInbound(input.readBytes(2)));
        try{
            channel.writeInbound(input.readBytes(4));
        }catch (TooLongFrameException e) {
            //e.printStackTrace();
        }
        Assert.assertTrue(channel.writeInbound(input.readBytes(3)));
        Assert.assertTrue(channel.finish());

        // read frames
        Assert.assertEquals(buf.readBytes(2),channel.readInbound());
        Assert.assertEquals(buf.skipBytes(4).readBytes(3),channel.readInbound());

    }


}
