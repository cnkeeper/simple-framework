package com.github.cnkeep.simple_springmvc.resolver.arguments;

import com.github.cnkeep.simple_springmvc.Resolver;
import com.github.cnkeep.simple_springmvc.handler.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 请求参数封装为方法参数
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public interface MethodHandlerArgResolver extends Resolver {
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
     * @param methodParameter
     * @return
     */
    Object handleArgument(HttpServletRequest request, HttpServletResponse response, MethodParameter methodParameter);
}
