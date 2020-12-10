package com.github.cnkeep.dubbo.serialize;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serialization {
    Serialize serialize(OutputStream outputStream) throws IOException;

    Deserialize deserialize(InputStream inputStream) throws IOException;
}
