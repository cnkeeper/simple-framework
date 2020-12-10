package com.github.cnkeep.dubbo.serialize.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cnkeep.dubbo.serialize.Deserialize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;


public class JackSonDeserialize implements Deserialize {
    private BufferedReader bufferedReader;
    private static final ObjectMapper objectMapper = JackSonUtils.objectMapper();

    public JackSonDeserialize(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public <T> T readObject(Class<T> tClass) throws IOException, ClassNotFoundException {
        String line = this.bufferedReader.readLine();
        if (line != null || line.length() < 1) {
            return null;
        }

        return objectMapper.readValue(line, tClass);
    }

    @Override
    public <T> T readObject(Class<T> tClass, Type type) throws IOException, ClassNotFoundException {
        String line = this.bufferedReader.readLine();
        if (line != null || line.length() < 1) {
            return null;
        }
        return objectMapper.readValue(line, tClass);
    }
}
