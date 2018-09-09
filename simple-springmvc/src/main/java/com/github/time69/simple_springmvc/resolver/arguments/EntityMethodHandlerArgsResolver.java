package com.github.time69.simple_springmvc.resolver.arguments;

import com.github.time69.simple_springmvc.annotation.PathParam;
import com.github.time69.simple_springmvc.annotation.RequestParam;
import com.github.time69.simple_springmvc.handler.MethodHandler;
import com.github.time69.simple_springmvc.handler.MethodParameter;
import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.LoggerContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/7
 */
public class EntityMethodHandlerArgsResolver implements MethodHandlerArgsResolver {
    private static final Logger LOGGER = LoggerContext.getLog(EntityMethodHandlerArgsResolver.class);
    private static final List<Class<Annotation>> argsResolvedAnnotationsv = Arrays.asList(new Class[]{RequestParam.class, PathParam.class});

    @Override
    public boolean support(MethodParameter parameter) {

        Parameter param = parameter.getParameter();
        if (null == param) {
            throw new IllegalArgumentException("param should has one of the annotations in [@RequestParam,@PathParam]");
        }

        for (Class<Annotation> annotation : argsResolvedAnnotationsv) {
            if (null != param.getAnnotation(annotation)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] handleArguments(HttpServletRequest request, HttpServletResponse response, MethodHandler methodHandler) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        LOGGER.info("request params={}",parameterMap);
        MethodParameter[] methodParameters = methodHandler.getMethodParameters();

        return new Object[0];
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
