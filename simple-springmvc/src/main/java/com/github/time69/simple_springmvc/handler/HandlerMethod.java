package com.github.time69.simple_springmvc.handler;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 描述: controller中对应的映射方法
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Data
public class HandlerMethod {
    private Class<?> controlerClass;
    private Object controllerBean;
    private Method method;
    private Object[] parameters;
}
