package com.dmtest.netty_learn.bytebuf;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

/**
 * 1. ByteBuf根据其分配空间不同有可以分为三种：JVM堆内的，直接内存，组合的。堆内受JVM的管辖，使用上相对安全一些，但是要注意GC是会影响性能。
 * 直接内存不受JVM的管控，在编程的时候要注意不用的ByteBuf要手动释放，否则会造成内存泄漏。
 *
 * 2. ByteBuf的创建：
 * a. Pooled
 * b. Unpooled
 * c. channel.alloc()
 *
 * 3. ByteBuf的内部可以分成三个部分：可读的，可写的，可丢弃的。
 * 可读空间由read index标记，可写空间由write index标记，可丢弃是已经读过的字节。
 *
 * 4. markReaderIndex可以对当前的reader index打一个标记，后续如果还有读操作，调用resetReaderIndex可以把readerIndex重置到原来的位置。
 *
 * 5. discardReadByte可以把读过的空间释放，这时buffer的可写空间和writerIndex会相应的改变。discardReadBytes在内存紧张的时候有用，但是
 * 调用该方法会伴随buffer的内存整理的．
 *
 * 6. buffer的派生操作，派生操作會产生一个新的byte buffer。这里的新指得是byte buf的引用是新的所有的index也是新的。但是他们共用一套存储。
 * duplicate，slice，order，readSlice
 *
 * 7. 通过hasArray检查一个ByteBuf heap based还是direct buffer
 *
 * 8. 如果想要复制一个ByteBuffer请使用copy
 *
 * 9. clear vs discardReadBytes。clear是把readerIndex和writerIndex重置到0，这时writable capacity恢复到原始值。
 * discardReadBytes只是把读过的字节丢弃，并进行一次内存整理。
 *
 * 10. 引用计数。引用计数记录了当前byteBuf被引用的次数。当refCnt == 0时，这个ByteBuf即可被release。
 */
public class ByteBufExample {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(0, 1024 * 1024);
        for (int i = 0; i < 256 * 1024; i++) {
            buffer.writeInt(i);
        }

        // 一个int占四个字节，所以1M最对能放256 × 1024个数字
        assert 0 == buffer.writableBytes();
        assert 1024 * 1024 == buffer.capacity();
        assert 1024 * 1024 == buffer.readableBytes();

        buffer.readBytes(20);
        assert 20 == buffer.readerIndex();
        buffer.markReaderIndex();
        buffer.readBytes(20);
        assert 40 == buffer.readerIndex();
        buffer.resetReaderIndex();
        assert 20 == buffer.readerIndex();

        buffer.discardReadBytes();
        assert 20 == buffer.writableBytes();

        ByteBuf buffer1 = Unpooled.buffer(0, 1024 * 1024);//heap buffer
        ByteBuf buffer2 = Unpooled.directBuffer(0, 1024 * 1024);//direct buffer

        assert buffer1.hasArray();
        assert !buffer2.hasArray();

        ByteBuf buffer3 = Unpooled.buffer(0, 1024);
        ByteBuf buffer4 = buffer3.duplicate();

        buffer3.writeInt(1);
        assert 4 == buffer3.writerIndex();
        assert 0 == buffer4.writerIndex();
        assert 1 == buffer4.array()[3];

        buffer3.readInt();
        assert 4 == buffer3.readerIndex();
        assert 0 == buffer4.readerIndex();


        buffer3.discardReadBytes();
        assert 0 == buffer3.readerIndex();
        assert 0 == buffer3.readerIndex();

        ByteBuf buffer5 = Unpooled.buffer();
        assert 1 == buffer5.refCnt();
        ByteBuf retainedBuffer = buffer5.retain();
        assert 2 == retainedBuffer.refCnt();
        assert retainedBuffer.release(2);


        ByteBuf buffer6 = Unpooled.buffer(0, 20);//heap buffer
        buffer6.writeInt(5).writeInt(5).writeInt(5).writeInt(5).writeInt(5);

        buffer6.readInt();
        assert 4 == buffer6.readerIndex();
        assert 20 == buffer6.writerIndex();
        assert 0 == buffer6.writableBytes();

        ByteBuf buffer7 = Unpooled.buffer();
        buffer7.writeBytes("123\rasdf\r\n".getBytes());
        int i = buffer7.forEachByte(ByteProcessor.FIND_CR);
        assert 3 == i;

        ByteBuf buffer8 = Unpooled.buffer(0, 20);//heap buffer
        buffer8.writeInt(5).writeInt(5).writeInt(5).writeInt(5).writeInt(5);
        buffer8.clear();

        assert 0 == buffer8.readerIndex();
        assert 0 == buffer8.writerIndex();
        assert 20 == buffer8.writableBytes();
        assert 20 == buffer8.capacity();
    }

}
