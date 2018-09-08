package com.github.time69.simple_springmvc.annotation;

import com.github.time69.simple_springmvc.http.HttpMethod;
import com.github.time69.simple_springmvc.http.MediaType;

import java.lang.annotation.*;

/**
 * 描述: 声明映射的注解，可用于class, method
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {
    /**
     * 路径
     */
    String path();

    /**
     * 支持的请求方法
     *
     * @return
     */
    HttpMethod[] method() default {HttpMethod.GET, HttpMethod.POST};

    /**
     * 请求内容格式
     *
     * @return
     */
    MediaType consume() default MediaType.ALL;

    /**
     * 返回响应的内容格式
     *
     * @return
     */
    MediaType produce() default MediaType.ALL;
}
