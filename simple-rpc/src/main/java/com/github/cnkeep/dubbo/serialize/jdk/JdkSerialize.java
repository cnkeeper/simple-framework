package com.github.cnkeep.dubbo.serialize.jdk;

import com.github.cnkeep.dubbo.serialize.Serialize;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class JdkSerialize implements Serialize {
    private ObjectOutputStream objectOutput;

    public JdkSerialize(OutputStream outputStream) throws IOException {
        this.objectOutput = new ObjectOutputStream(outputStream);
    }

    @Override
    public void writeObject(Object object) throws IOException {
        objectOutput.writeObject(object);
    }

    @Override
    public void writeShort(short v) throws IOException {
        objectOutput.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        objectOutput.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        objectOutput.writeLong(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        objectOutput.writeInt(v.length());
        objectOutput.writeUTF(v);
    }

    @Override
    public void flushBuffer() throws IOException {
        objectOutput.flush();
    }
}
