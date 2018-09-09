package com.github.time69.simple_springmvc.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @description Json序列化工具类,使用fasterxml.jackjson对json进行序列化
 * @author <a href="zhangleili@wxchina.com">LeiLi.Zhang</a>
 * @date 2017年12月12日
 */
public class JsonUtil {
    private final static ObjectMapper mapper = new ObjectMapper();

    /**
     * @description 序列化
     * @param value
     * @return String
     * @throws JsonProcessingException
     */
    public static String serialize(Object value) throws JsonProcessingException{
        return mapper.writeValueAsString(value);
    }

    /**
     * @description 序列化
     * @param value
     * @return byte[]
     * @throws JsonProcessingException
     */
    public static byte[] serializeToByte(Object value) throws JsonProcessingException{
        return mapper.writeValueAsBytes(value);
    }

    /**
     * @description 反序列化
     * @param content
     * @param valueType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T deserialize(String content,Class<T> valueType) throws JsonParseException, JsonMappingException, IOException{
        return mapper.readValue(content, valueType);
    }

    /**
     * @description 反序列化
     * @param content
     * @param valueType
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T deserializeFromByte(byte[] content,Class<T> valueType) throws JsonParseException, JsonMappingException, IOException{
        return mapper.readValue(content, valueType);
    }
}
