package com.github.cnkeep.dubbo.serialize.jdk;

import com.github.cnkeep.dubbo.serialize.Deserialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;


public class JdkDeserialize implements Deserialize {

    private ObjectInputStream inputStream;

    public JdkDeserialize(InputStream inputStream) throws IOException {
        this.inputStream = new ObjectInputStream(inputStream);
    }

    @Override
    public <T> T readObject(Class<T> tClass) throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }

    @Override
    public <T> T readObject(Class<T> tClass, Type type) throws IOException, ClassNotFoundException {
        return (T) inputStream.readObject();
    }
}
