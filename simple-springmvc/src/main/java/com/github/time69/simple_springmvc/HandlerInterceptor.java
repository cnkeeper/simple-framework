package com.github.time69.simple_springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 拦截器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface HandlerInterceptor {
    /**
     * 请求前置拦截器，在请求被处理前做拦截
     * @param httpServletRequest
     * @param response
     * @param handler
     * @return true:继续执行拦截器链; false:终止执行拦截器链
     * @throws Exception
     */
    boolean preHandler(HttpServletRequest httpServletRequest, HttpServletResponse response, Handler handler) throws Exception;

    /**
     * 后置处理器，在请求被处理完后做处理
     * @param httpServletRequest
     * @param response
     * @param handler
     * @param modelAndView
     * @return
     * @throws Exception
     */
    boolean postHandler(HttpServletRequest httpServletRequest, HttpServletResponse response, Handler handler, ModelAndView modelAndView) throws Exception;
}
