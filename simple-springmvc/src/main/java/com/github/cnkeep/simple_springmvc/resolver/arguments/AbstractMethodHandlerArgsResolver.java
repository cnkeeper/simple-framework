package com.github.cnkeep.simple_springmvc.resolver.arguments;

import com.github.cnkeep.simple_springmvc.handler.MethodParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/9
 */
public abstract class AbstractMethodHandlerArgsResolver implements MethodHandlerArgResolver {
    @Override
    public boolean support(MethodParameter parameter) {
        Parameter param = parameter.getParameter();
        if (null == param) {
            throw new IllegalArgumentException("param should has one of the annotations in [@RequestParam,@PathParam]");
        }

        return (null != param.getAnnotation(getSupportedAnnotation()));
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 返回该参数解析器支持的参数注解
     * @return
     */
    public abstract Class<? extends Annotation> getSupportedAnnotation();
}
