package com.github.cnkeep.simple_springmvc.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * JacksonUtils
 *
 * @author LeiLi.Zhang <mailto:zhangleili 924@gmail.com>
 * @date 2022/7/4
 */
public class JacksonUtils {
    private JacksonUtils() {
    }

    private static ObjectMapper getMapper() {
        return SingletonHolder.mapper;
    }

    /**
     * 将对象转换为json字符串
     *
     * @param pojo 对象
     * @return 单行json字符串
     */
    public static String toJsonString(Object pojo) {
        if (pojo == null) {
            return null;
        }
        try {
            return getMapper().writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象转换为格式化的json字符串
     *
     * @param pojo 对象
     * @return 格式化的json字符串
     */
    public static String toPrettyJsonString(Object pojo) {
        if (pojo == null) {
            return null;
        }
        try {
            return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 将json字符串转换为java对象，只支持返回简单对象（非集合类型）
     *
     * @param jsonString json字符串
     * @param valueType  期望类型
     * @return 转换后的结果
     * @throws IOException 转换失败的时候抛出
     */
    public static <T> T parseToObject(String jsonString, Class<T> valueType) throws IOException {
        if (StringUtils.isNotBlank(jsonString)) {
            return getMapper().readValue(jsonString, valueType);
        }
        return null;
    }

    /**
     * 将json字符串转换为java对象
     *
     * @param jsonString    原始的json字符串
     * @param typeReference 复杂类型的类型引用
     * @param <T>           目标类型
     * @return 转换后的结果
     * @throws IOException 转换失败的时候抛出
     */
    public static <T> T parseToObject(String jsonString, TypeReference<T> typeReference) throws IOException {
        return getMapper().readValue(jsonString, typeReference);
    }


    /**
     * ObjectMapper 提供单例供全局使用
     */
    private static class SingletonHolder {

        private static final ObjectMapper mapper;

        static {
            mapper = new ObjectMapper();
            // 设置日期对象的输出格式
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINESE));
            // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
            simpleModule.addSerializer(FileDescriptor.class, new JsonSerializer<FileDescriptor>() {
                @Override
                public void serialize(FileDescriptor fileDescriptor, JsonGenerator jsonGenerator,
                                      SerializerProvider serializerProvider) throws IOException {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("file", "f");
                    jsonGenerator.writeEndObject();
                }
            });
            mapper.registerModule(simpleModule);
        }
    }

}
