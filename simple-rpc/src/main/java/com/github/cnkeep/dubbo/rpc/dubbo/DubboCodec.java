package com.github.cnkeep.dubbo.rpc.dubbo;


import com.github.cnkeep.dubbo.common.util.Bytes;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.buffer.ChannelBuffer;
import com.github.cnkeep.dubbo.rpc.buffer.ChannelBufferOutputStream;
import com.github.cnkeep.dubbo.serialize.Serialize;
import com.github.cnkeep.dubbo.serialize.jdk.JdkSerialization;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-12 11:48
 * @version:
 **/
public class DubboCodec {
    private AtomicLong sequence = new AtomicLong(0);
    private static final short HEADER_LEN = 16;
    private static final short MAGIC_NUM = 0x01;

    // message flag.
    protected static final byte FLAG_REQUEST = (byte) 0x80;
    protected static final byte FLAG_TWOWAY = (byte) 0x40;
    protected static final byte FLAG_EVENT = (byte) 0x20;

    /**
     * see https://dubbo.apache.org/zh/docs/v2.7/dev/implementation/
     *
     * @param invocation
     * @param buffer
     * @throws IOException
     */
    public void encode(Invocation invocation, ChannelBuffer buffer) throws IOException {
        byte[] header = new byte[HEADER_LEN];
        Bytes.writeShort(MAGIC_NUM, header, 0);
        boolean isRequest = true;
        boolean isTwoWay = true;
        boolean isEvent = true;
        byte serializationType = 6;
        header[2] = (byte) (FLAG_REQUEST | serializationType);
        if (isTwoWay) {
            header[2] |= FLAG_TWOWAY;
        }

        if (isEvent) {
            header[2] |= FLAG_EVENT;
        }

        int savedWriteIndex = buffer.writerIndex();
        buffer.writerIndex(savedWriteIndex + HEADER_LEN);
        ChannelBufferOutputStream outputStream = new ChannelBufferOutputStream(buffer);
        Serialize serialize = new JdkSerialization().serialize(outputStream);
        // body
        serialize.writeUTF(invocation.getServiceName());
        serialize.writeUTF(invocation.getMethodName());
        serialize.writeObject(invocation.getParameterTypes());
        serialize.writeObject(invocation.getParameters());
        serialize.writeObject(invocation.getAttachments());
        outputStream.flush();
        outputStream.close();

        int bodyLen = outputStream.writtenBytes();
        // header=16
        buffer.writerIndex(savedWriteIndex);
        buffer.writeBytes(header);
        buffer.writerIndex(savedWriteIndex + HEADER_LEN + bodyLen);
    }

    public void decode(){
        // TODO
    }
}
