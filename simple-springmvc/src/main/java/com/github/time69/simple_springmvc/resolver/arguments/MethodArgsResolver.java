package com.github.time69.simple_springmvc.resolver.arguments;

import com.github.time69.simple_springmvc.Resolver;
import com.github.time69.simple_springmvc.handler.MethodHandler;
import com.github.time69.simple_springmvc.handler.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 请求参数封装为方法参数
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public interface MethodArgsResolver extends Resolver {
    boolean support(MethodParameter parameter);
    Object[] handleMethodArgs(HttpServletRequest request, HttpServletResponse response, MethodHandler methodHandler);
}
