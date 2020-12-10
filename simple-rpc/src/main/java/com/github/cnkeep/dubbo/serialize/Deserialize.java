package com.github.cnkeep.dubbo.serialize;


import java.io.IOException;
import java.lang.reflect.Type;

public interface Deserialize {
    <T> T readObject(Class<T> tClass) throws IOException, ClassNotFoundException;

    <T> T readObject(Class<T> tClass, Type type) throws IOException, ClassNotFoundException;
}
