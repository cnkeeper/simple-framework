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
public interface MethodHandlerArgsResolver extends Resolver {
    /**
     * 是否支持处理该参数
     * @param parameter
     * @return
     */
    boolean support(MethodParameter parameter);

    /**
     * 请求参数封装为对象
     * @param request
     * @param response
     * @param methodHandler
     * @return
     */
    Object[] handleArguments(HttpServletRequest request, HttpServletResponse response, MethodHandler methodHandler);
}
