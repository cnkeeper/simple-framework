package com.github.cnkeep.dubbo.rpc.dubbo;


import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.serialize.Serialize;
import com.github.cnkeep.dubbo.serialize.jdk.JdkSerialization;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

public class DubboCodec {
    private AtomicLong sequence = new AtomicLong(0);


    public void encode(Invocation invocation, OutputStream outputStream) throws IOException {
        Serialize serialize = new JdkSerialization().serialize(outputStream);
        long dataLen = 0L;

        // body
        serialize.writeUTF(invocation.getServiceName());
        serialize.writeUTF(invocation.getMethodName());
        serialize.writeObject(invocation.getParameterTypes());
        serialize.writeObject(invocation.getParameters());


        serialize.writeObject(invocation.getParameterTypes());
        // header=16
        serialize.writeShort((short) 0222);
        serialize.writeShort((short) 0x01);
        serialize.writeLong(sequence.getAndIncrement());
        serialize.writeLong(dataLen);


    }
}
