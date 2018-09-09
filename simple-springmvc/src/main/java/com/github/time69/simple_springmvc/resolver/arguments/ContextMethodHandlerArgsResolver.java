package com.github.time69.simple_springmvc.resolver.arguments;

import com.github.time69.simple_springmvc.annotation.Context;
import com.github.time69.simple_springmvc.handler.MethodHandler;
import com.github.time69.simple_springmvc.handler.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * 描述: @Context注解支持，注入HttpServletRequest,HttpServletResponse
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/9
 */
public class ContextMethodHandlerArgsResolver extends AbstractMethodHandlerArgsResolver {
    @Override
    public boolean support(MethodParameter parameter) {
        return super.support(parameter)
                && (HttpServletRequest.class.isAssignableFrom(parameter.getParameterType())
                || HttpServletResponse.class.isAssignableFrom(parameter.getParameterType()));
    }

    @Override
    public Object handleArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(HttpServletResponse.class) ? response : request;
    }

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return Context.class;
    }
}
