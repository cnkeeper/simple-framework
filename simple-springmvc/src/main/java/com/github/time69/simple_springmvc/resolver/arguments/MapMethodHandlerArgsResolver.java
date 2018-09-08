package com.github.time69.simple_springmvc.resolver.arguments;

import com.github.time69.simple_springmvc.handler.MethodHandler;
import com.github.time69.simple_springmvc.handler.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 默认的请求参数解析器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/7
 */
public class MapMethodHandlerArgsResolver implements MethodHandlerArgsResolver {
    @Override
    public boolean support(MethodParameter parameter) {
        return false;
    }

    @Override
    public Object[] handleArguments(HttpServletRequest request, HttpServletResponse response, MethodHandler methodHandler) {
        return new Object[0];
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
