package com.github.cnkeep.dubbo.rpc.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @description: HeapChannelBuffer
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-12 16:0
 * @version: v0.0.1
 **/
public class HeapChannelBuffer extends AbstractChannelBuffer {
    protected final byte[] array;
    private ChannelBufferFactory factory;

    public HeapChannelBuffer(int length) {
        this(new byte[length], 0, 0);
    }

    public HeapChannelBuffer(byte[] array) {
        this(array, 0, 0);
    }

    protected HeapChannelBuffer(byte[] array, int readerIndex, int writerIndex) {
        if (array == null) {
            throw new NullPointerException("array");
        }
        this.array = array;
        setIndex(readerIndex, writerIndex);
        this.factory = HeapChannelBufferFactory.getInstance();
    }

    @Override
    public int capacity() {
        return this.array.length;
    }

    @Override
    public ChannelBuffer copy(int index, int length) {
        if (index < 0 || length < 0 || index + length > array.length) {
            throw new IndexOutOfBoundsException();
        }
        byte[] copy = new byte[length];
        System.arraycopy(array, index, copy, 0, length);
        return new HeapChannelBuffer(copy);
    }

    @Override
    public ChannelBufferFactory factory() {
        return factory;
    }

    @Override
    public byte getByte(int index) {
        return array[index];
    }


    @Override
    public void getBytes(int index, byte[] dst, int dstIndex, int length) {
        System.arraycopy(array, index, dst, dstIndex, length);
    }

    @Override
    public void getBytes(int index, ByteBuffer dst) {
        dst.put(array, index, Math.min(capacity() - index, dst.remaining()));
    }

    @Override
    public void getBytes(int index, OutputStream dst, int length) throws IOException {
        dst.write(array, index, length);
    }

    @Override
    public void setByte(int index, int value) {
        array[index] = (byte) value;
    }

    @Override
    public void setBytes(int index, byte[] src, int srcIndex, int length) {
        System.arraycopy(src, srcIndex, array, index, length);
    }

    @Override
    public void setBytes(int index, ByteBuffer src) {
        src.get(array, index, src.remaining());
    }

    @Override
    public void setBytes(int index, ChannelBuffer src, int srcIndex, int length) {
        setBytes(index, src.array(), srcIndex, length);
    }

    @Override
    public int setBytes(int index, InputStream src, int length) throws IOException {
        int readBytes = 0;
        do {
            int localReadBytes = src.read(array, index, length);
            if (localReadBytes < 0) {
                if (readBytes == 0) {
                    return -1;
                } else {
                    break;
                }
            }
            readBytes += localReadBytes;
            index += localReadBytes;
            length -= localReadBytes;
        } while (length > 0);

        return readBytes;
    }

    @Override
    public ByteBuffer toByteBuffer(int index, int length) {
        return ByteBuffer.wrap(array, index, length);
    }

    @Override
    public byte[] array() {
        return array;
    }

    @Override
    public boolean hasArray() {
        return true;
    }

    @Override
    public int arrayOffset() {
        return 0;
    }

    @Override
    public int compareTo(ChannelBuffer o) {
        return 0;
    }
}
