package com.github.time69.simple_springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 描述： 处理器执行器：调用Handler处理请求
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface HandlerAdapter {
    /**
     * 执行器是否支持执行该处理器
     * @param handler
     * @return
     */
    boolean supports(Handler handler);

    /**
     * 执行处理器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception;
}
