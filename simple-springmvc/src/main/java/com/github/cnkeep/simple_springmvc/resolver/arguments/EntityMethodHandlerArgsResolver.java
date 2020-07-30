package com.github.cnkeep.simple_springmvc.resolver.arguments;

import com.github.cnkeep.simple_springmvc.handler.MethodParameter;
import com.github.cnkeep.simple_springmvc.logger.Logger;
import com.github.cnkeep.simple_springmvc.logger.LoggerContext;
import com.github.cnkeep.simple_springmvc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 描述： 自动封装参数为实体类对象注入
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/7
 */
public class EntityMethodHandlerArgsResolver extends AbstractMethodHandlerArgsResolver {
    private static final Logger LOGGER = LoggerContext.getLog(EntityMethodHandlerArgsResolver.class);

    @Override
    public Object handleArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter) {

        Map<String, String[]> parameterMap = request.getParameterMap();

        LOGGER.info("request params={}",parameterMap);

        return new Object();
    }

    @Override
    public Class<? extends Annotation> getSupportedAnnotation() {
        return RequestParam.class;
    }
}
