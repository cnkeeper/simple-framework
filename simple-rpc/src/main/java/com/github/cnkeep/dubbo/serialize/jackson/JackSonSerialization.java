package com.github.cnkeep.dubbo.serialize.jackson;

import com.github.cnkeep.dubbo.serialize.Deserialize;
import com.github.cnkeep.dubbo.serialize.Serialization;
import com.github.cnkeep.dubbo.serialize.Serialize;

import java.io.InputStream;
import java.io.OutputStream;

public class JackSonSerialization implements Serialization {
    @Override
    public Serialize serialize(OutputStream outputStream) {
        return new JackSonSerialize(outputStream);
    }

    @Override
    public Deserialize deserialize(InputStream inputStream) {
        return new JackSonDeserialize(inputStream);
    }
}
