package com.github.cnkeep.simple_springmvc.handler.support.mapping;

import com.github.cnkeep.simple_springmvc.HandlerInterceptor;
import com.github.cnkeep.simple_springmvc.HandlerMapping;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public abstract class AbstractHandlerMapping implements HandlerMapping {
    protected HandlerInterceptor[] handlerInterceptors = new HandlerInterceptor[0];
}
