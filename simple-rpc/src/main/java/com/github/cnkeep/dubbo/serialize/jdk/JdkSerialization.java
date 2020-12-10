package com.github.cnkeep.dubbo.serialize.jdk;

import com.github.cnkeep.dubbo.serialize.Deserialize;
import com.github.cnkeep.dubbo.serialize.Serialization;
import com.github.cnkeep.dubbo.serialize.Serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class JdkSerialization implements Serialization {
    @Override
    public Serialize serialize(OutputStream outputStream) throws IOException {
        return new JdkSerialize(outputStream);
    }

    @Override
    public Deserialize deserialize(InputStream inputStream) throws IOException {
        return new JdkDeserialize(inputStream);
    }
}
