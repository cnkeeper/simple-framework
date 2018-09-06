package com.github.time69.simple_springmvc.resolver.returnValue;

import com.github.time69.simple_springmvc.Resolver;
import com.github.time69.simple_springmvc.handler.MethodHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 方法执行器的执行结果解析器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public interface MethodHandlerReturnValueResolver extends Resolver{
    /**
     * 是否由该解析器解析处理
     *
     * @param methodHandler
     * @return
     */
    boolean support(MethodHandler methodHandler);

    /**
     * 处理方法返回结果
     * @param request
     * @param response
     * @param returnValue controller方法的返回值
     */
    void handleReturnValue(HttpServletRequest request, HttpServletResponse response, Object returnValue);
}
