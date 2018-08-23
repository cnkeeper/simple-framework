package com.github.time69.simple_framework.annotation;

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
    /*路径*/
    String path();

    /*请求方法*/
    Method[] method() default {Method.GET, Method.POST};

    enum Method {
        GET, POST, PUT, OPTION, DELETE;
    }
}
