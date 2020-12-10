package com.github.cnkeep.dubbo.serialize.jackson;

import com.github.cnkeep.dubbo.serialize.Serialize;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-10 17:40
 * @version:
 **/

public class JackSonSerialize implements Serialize {
    private BufferedWriter bufferedWriter;


    public JackSonSerialize(OutputStream outputStream){
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    public void writeObject(Object object) throws IOException {
        String line = JackSonUtils.objectMapper().writeValueAsString(object);
        this.bufferedWriter.write(line);
        this.bufferedWriter.newLine();
        this.bufferedWriter.flush();
    }

    @Override
    public void writeShort(short v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        writeObject(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        writeObject(v);
    }

    @Override
    public void flushBuffer() throws IOException {
        bufferedWriter.flush();
    }
}
