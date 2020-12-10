package com.github.cnkeep.dubbo.serialize;


import java.io.IOException;

public interface Serialize {
    void writeObject(Object object) throws IOException;

    void writeShort(short v) throws IOException;

    void writeInt(int v) throws IOException;

    void writeLong(long v) throws IOException;

    void writeUTF(String v) throws IOException;

    void flushBuffer() throws IOException;
}
