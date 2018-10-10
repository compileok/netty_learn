package com.dmtest.netty_learn.chapter07;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * 2018/10/9.
 */
public class CharCodec7_13 extends CombinedChannelDuplexHandler<ByteToCharDecoder7_11,CharToByteEncoder7_12>{

    public CharCodec7_13() {
        super(new ByteToCharDecoder7_11(),new CharToByteEncoder7_12());
    }
}
