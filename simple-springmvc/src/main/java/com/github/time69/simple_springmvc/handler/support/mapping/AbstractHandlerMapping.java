package com.github.time69.simple_springmvc.handler.support.mapping;

import com.github.time69.simple_springmvc.HandlerInterceptor;
import com.github.time69.simple_springmvc.HandlerMapping;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {
    protected HandlerInterceptor[] handlerInterceptors;
}
