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
    void handler(HttpServletRequest httpServletRequest, HttpServletResponse response);
}
