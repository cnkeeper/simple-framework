package com.github.cnkeep.dubbo.rpc.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @description: AbstractChannelBuffer
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-12 16:25
 * @version: v0.0.1
 **/
public abstract class AbstractChannelBuffer implements ChannelBuffer {
    private int readerIndex;
    private int writerIndex;
    private int markedReaderIndex;
    private int markedWriterIndex;

    @Override
    public void clear() {
        readerIndex = 0;
        writerIndex = 0;
    }

    @Override
    public ChannelBuffer copy() {
        return copy(readerIndex, readableBytes());
    }

    @Override
    public void discardReadBytes() {
        setBytes(0, this, readerIndex, readableBytes());
        markedWriterIndex = Math.max(markedWriterIndex - readerIndex, 0);
        markedReaderIndex = Math.max(markedReaderIndex - readerIndex, 0);
        writerIndex -= readerIndex;
        readerIndex = 0;
    }

    @Override
    public void ensureWritableBytes(int writableBytes) {
        if (writerIndex + writableBytes > capacity()) {
            throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public void getBytes(int index, byte[] dst) {
        getBytes(index, dst, 0, dst.length);
    }

    @Override
    public void getBytes(int index, ChannelBuffer dst, int length) {
        getBytes(index, dst, dst.writerIndex() + 1, length);
    }

    @Override
    public void getBytes(int index, ChannelBuffer dst, int dstIndex, int length) {
        getBytes(index, dst.array(), dstIndex, length);
    }

    @Override
    public void getBytes(int index, ChannelBuffer dst) {
        getBytes(index, dst, dst.writableBytes());
    }

    @Override
    public boolean isDirect() {
        return false;
    }

    @Override
    public void markReaderIndex() {
        this.markedReaderIndex = readerIndex;
    }

    @Override
    public void markWriterIndex() {
        this.markedWriterIndex = writerIndex;
    }

    @Override
    public boolean readable() {
        return readerIndex() > 0;
    }

    @Override
    public int readableBytes() {
        return writerIndex - readerIndex;
    }

    @Override
    public byte readByte() {
        return getByte(readerIndex++);
    }

    @Override
    public void readBytes(byte[] dst) {
        readBytes(dst, 0, dst.length);
    }

    @Override
    public void readBytes(byte[] dst, int dstIndex, int length) {
        getBytes(readerIndex, dst, dstIndex, length);
    }

    @Override
    public void readBytes(ChannelBuffer dst) {
        readBytes(dst, dst.writerIndex(), dst.writableBytes());
    }

    @Override
    public void readBytes(ChannelBuffer dst, int length) {
        readBytes(dst, dst.writerIndex(), length);
    }

    @Override
    public ChannelBuffer readBytes(int length) {
        checkReadableBytes(length);
        if (length == 0) {
            return ChannelBuffers.EMPTY_BUFFER;
        }
        ChannelBuffer buf = factory().getBuffer(length);
        buf.writeBytes(this, readerIndex, length);
        readerIndex += length;
        return buf;
    }

    @Override
    public void resetReaderIndex() {
        readerIndex(this.markedReaderIndex);
    }

    @Override
    public void resetWriterIndex() {
        writerIndex(this.markedWriterIndex);
    }

    @Override
    public int readerIndex() {
        return this.readerIndex;
    }

    @Override
    public void readerIndex(int readerIndex) {
        if (readerIndex < 0 || readerIndex > writerIndex) {
            throw new IndexOutOfBoundsException();
        }
        this.readerIndex = readerIndex;
    }

    @Override
    public void readBytes(ByteBuffer dst) {
        int remaining = dst.remaining();
        getBytes(readerIndex(), dst);
        readerIndex += remaining;
    }

    @Override
    public void readBytes(OutputStream dst, int length) throws IOException {
        checkReadableBytes(length);
        getBytes(readerIndex, dst, length);
        readerIndex += length;
    }

    @Override
    public void readBytes(ChannelBuffer dst, int dstIndex, int length) {
        checkReadableBytes(length);
        getBytes(readerIndex(), dst, dstIndex, length);
        readerIndex += length;
    }

    @Override
    public void setBytes(int index, byte[] src) {
        setBytes(index, src, 0, src.length);
    }


    @Override
    public void setBytes(int index, ChannelBuffer src) {
        setBytes(index, src, src.readerIndex(), src.readableBytes());
    }

    @Override
    public void setBytes(int index, ChannelBuffer src, int length) {
        setBytes(index, src, src.readerIndex(), length);
    }

    @Override
    public void setIndex(int readerIndex, int writerIndex) {
        this.readerIndex = readerIndex;
        this.writerIndex = writerIndex;
    }

    @Override
    public void skipBytes(int length) {
        int newReadIndex = readerIndex + length;
        if (newReadIndex > writerIndex) {
            throw new IndexOutOfBoundsException();
        }
        this.readerIndex = newReadIndex;
    }

    @Override
    public ByteBuffer toByteBuffer() {
        return toByteBuffer(readerIndex, readableBytes());
    }

    @Override
    public boolean writable() {
        return writableBytes() > 0;
    }

    @Override
    public int writableBytes() {
        return capacity() - writerIndex;
    }

    @Override
    public void writeByte(int value) {
        setByte(writerIndex++, value);
    }

    @Override
    public void writeBytes(byte[] src) {
        writeBytes(src, 0, src.length);
    }

    @Override
    public void writeBytes(byte[] src, int index, int length) {
        setBytes(writerIndex++, src, index, length);
    }

    @Override
    public void writeBytes(ChannelBuffer src) {
        writeBytes(src, src.readableBytes());
    }


    @Override
    public void writeBytes(ByteBuffer src) {
        int length = src.remaining();
        setBytes(writerIndex, src);
        writerIndex += length;
    }

    @Override
    public void writeBytes(ChannelBuffer src, int length) {
        writeBytes(src, src.readerIndex(), length);
    }


    @Override
    public void writeBytes(ChannelBuffer src, int srcIndex, int length) {
        setBytes(writerIndex, src, srcIndex, length);
        writerIndex += length;
    }


    @Override
    public int writeBytes(InputStream in, int length) throws IOException {
        int writtenBytes = setBytes(writerIndex, in, length);
        if (writtenBytes > 0) {
            writerIndex += writtenBytes;
        }
        return writtenBytes;
    }

    @Override
    public int writerIndex() {
        return this.writerIndex;
    }

    @Override
    public void writerIndex(int writerIndex) {
        this.writerIndex = writerIndex;
    }

    protected void checkReadableBytes(int minimumReadableBytes) {
        if (readableBytes() < minimumReadableBytes) {
            throw new IndexOutOfBoundsException();
        }
    }
}
