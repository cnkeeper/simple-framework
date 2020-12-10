package com.github.cnkeep.dubbo.serialize.jackson;

import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;


public class JackSonSerializationTest {
    private JackSonSerialization serialization;

    @Before
    public void init() {
        serialization = new JackSonSerialization();
    }

    @Test
    public void serialize() throws IOException {
        Protocol protocol = new Protocol();
        protocol.setBody("hello world");
        protocol.setVersion(1);

        serialization.serialize(System.out).serialize(protocol);
    }

    @Test
    public void deserialize() throws IOException, ClassNotFoundException {
        String line = "{\"version\":1,\"body\":\"hello world\"}";
        Protocol protocol = JackSonUtils.objectMapper().readValue(line,Protocol.class);
        System.out.println(protocol);
    }

    @Data
    private static class Protocol{
        private int version;
        private String body;
    }
}