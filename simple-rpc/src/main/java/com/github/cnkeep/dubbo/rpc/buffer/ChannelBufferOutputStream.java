package com.github.cnkeep.dubbo.rpc.buffer;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @description: 自定义基于byteBuffer的流
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-10 23:34
 * @version: v0.0.1
 **/
public class ChannelBufferOutputStream extends OutputStream {
    private ChannelBuffer buffer;
    private int startIndex;

    public ChannelBufferOutputStream(ChannelBuffer buffer) {
        this.buffer = buffer;
        startIndex = buffer.writerIndex();
    }

    @Override
    public void write(int b) throws IOException {
        buffer.writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        buffer.writeBytes(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        buffer.writeBytes(b, off, len);
    }

    public ChannelBuffer getBuffer() {
        return buffer;
    }

    public int writtenBytes() {
        return buffer.writerIndex() - startIndex;
    }
}
