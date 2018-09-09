package com.github.time69.simple_springmvc.resolver.arguments;

import com.github.time69.simple_springmvc.annotation.RequestParam;
import com.github.time69.simple_springmvc.handler.MethodHandler;
import com.github.time69.simple_springmvc.handler.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

/**
 * 描述: 以map的性质注入参数
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/7
 */
public class MapMethodHandlerArgsResolver extends AbstractMethodHandlerArgsResolver {
    @Override
    public boolean support(MethodParameter parameter) {
        return super.support(parameter) && Map.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object handleArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {
        return request.getParameterMap();
    }

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return RequestParam.class;
    }
}
