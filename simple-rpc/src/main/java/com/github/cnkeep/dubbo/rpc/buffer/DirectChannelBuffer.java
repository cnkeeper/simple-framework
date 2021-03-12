package com.github.cnkeep.dubbo.rpc.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/1/13
 * @version:
 **/
public class DirectChannelBuffer extends AbstractChannelBuffer {
    @Override
    public int capacity() {
        return 0;
    }

    @Override
    public ChannelBuffer copy(int index, int length) {
        return null;
    }

    @Override
    public ChannelBufferFactory factory() {
        return DirectChannelBufferFactory.getInstance();
    }

    @Override
    public byte getByte(int index) {
        return 0;
    }

    @Override
    public void getBytes(int index, byte[] dst, int dstIndex, int length) {

    }

    @Override
    public void getBytes(int index, ByteBuffer dst) {

    }

    @Override
    public void getBytes(int index, ChannelBuffer dst, int length) {

    }

    @Override
    public void getBytes(int index, ChannelBuffer dst, int dstIndex, int length) {

    }

    @Override
    public void getBytes(int index, OutputStream dst, int length) throws IOException {

    }

    @Override
    public void setByte(int index, int value) {

    }

    @Override
    public void setBytes(int index, byte[] src, int srcIndex, int length) {

    }

    @Override
    public void setBytes(int index, ByteBuffer src) {

    }

    @Override
    public void setBytes(int index, ChannelBuffer src, int srcIndex, int length) {

    }

    @Override
    public int setBytes(int index, InputStream src, int length) throws IOException {
        return 0;
    }

    @Override
    public ByteBuffer toByteBuffer(int index, int length) {
        return null;
    }

    @Override
    public byte[] array() {
        return new byte[0];
    }

    @Override
    public boolean hasArray() {
        return false;
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
