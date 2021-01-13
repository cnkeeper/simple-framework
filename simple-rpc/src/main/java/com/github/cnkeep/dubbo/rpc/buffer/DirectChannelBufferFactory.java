package com.github.cnkeep.dubbo.rpc.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/1/13
 * @version:
 **/
public class DirectChannelBufferFactory implements ChannelBufferFactory {
    private static final ChannelBufferFactory INSTANCE = new DirectChannelBufferFactory();

    private DirectChannelBufferFactory() {

    }

    @Override
    public ChannelBuffer getBuffer(int capacity) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(ByteOrder endianness, int capacity) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(byte[] array, int offset, int length) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(ByteOrder endianness, byte[] array, int offset, int length) {
        return null;
    }

    @Override
    public ChannelBuffer getBuffer(ByteBuffer nioBuffer) {
        return null;
    }

    @Override
    public ByteOrder getDefaultOrder() {
        return null;
    }


    public static ChannelBufferFactory getInstance() {
        return INSTANCE;
    }
}
