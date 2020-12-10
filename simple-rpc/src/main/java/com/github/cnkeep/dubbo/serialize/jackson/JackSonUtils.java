package com.github.cnkeep.dubbo.serialize.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JackSonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper objectMapper() {
        return objectMapper;
    }
}
