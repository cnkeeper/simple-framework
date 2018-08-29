package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface HandlerMapping {
    HandlerExecutionChain getHander(HttpServletRequest httpServletRequest);
}
