package com.github.cnkeep.dubbo.rpc.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-12 16:2
 * @version:
 **/
public class HeapChannelBufferFactory implements ChannelBufferFactory {
    private static final ChannelBufferFactory INSTANCE = new HeapChannelBufferFactory();

    @Override
    public ChannelBuffer getBuffer(int capacity) {
        return new HeapChannelBuffer(capacity);
    }

    @Override
    public ChannelBuffer getBuffer(ByteOrder endianness, int capacity) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(byte[] array, int offset, int length) {
        return new HeapChannelBuffer(array, offset, length);
    }

    @Override
    public ChannelBuffer getBuffer(ByteOrder endianness, byte[] array, int offset, int length) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(ByteBuffer nioBuffer) {
        return new HeapChannelBuffer(nioBuffer.array(), nioBuffer.arrayOffset() + nioBuffer.position(), nioBuffer.remaining());
    }

    @Override
    public ByteOrder getDefaultOrder() {
        return null;
    }

    public static ChannelBufferFactory getInstance() {
        return INSTANCE;
    }
}
