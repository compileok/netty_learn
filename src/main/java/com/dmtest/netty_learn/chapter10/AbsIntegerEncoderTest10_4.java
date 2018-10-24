package com.dmtest.netty_learn.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Listing 10.4 Test the AbsIntegerEncoder
 * 2018/10/24.
 */
public class AbsIntegerEncoderTest10_4 {

    @Test
    public void testEncoded(){
        ByteBuf buf = Unpooled.buffer();

        for (int i = 0; i < 10; i++) {
            buf.writeInt(i * -1);
        }

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder10_3());

        Assert.assertTrue(channel.writeOutbound(buf));
        Assert.assertTrue(channel.finish());

        // read bytes
        ByteBuf output = (ByteBuf) channel.readOutbound();
        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(i,output.readInt());
        }
        Assert.assertFalse(output.isReadable());
        Assert.assertNull(channel.readOutbound());
    }


}
