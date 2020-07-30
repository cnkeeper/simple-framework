package com.github.cnkeep.simple_springmvc.resolver.arguments;

import com.github.cnkeep.simple_springmvc.handler.MethodParameter;
import com.github.cnkeep.simple_springmvc.annotation.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * 描述: @Context注解支持，注入HttpServletRequest,HttpServletResponse
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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
