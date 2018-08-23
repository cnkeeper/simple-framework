package com.github.time69.simple_framework.handler;

import com.github.time69.simple_framework.HandlerInterceptor;
import org.omg.PortableInterceptor.Interceptor;

import java.lang.reflect.Method;

/**
 * 描述: 拦截器链
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public class HandlerExecutionChain {
    //实际处理的对象，其实就是controller中的method
    private Object handler;
    private HandlerInterceptor[] handlerInterceptors;

    public HandlerExecutionChain(Method handler) {
        this(handler, new HandlerInterceptor[0]);
    }

    public HandlerExecutionChain(Method handler, HandlerInterceptor[] handlerInterceptors) {
        this.handler = handler;
        this.handlerInterceptors = handlerInterceptors;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }
}
