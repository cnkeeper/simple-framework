package com.github.time69.simple_springmvc.resolver;

import com.github.time69.simple_springmvc.Resolver;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public abstract class AbstractSupportResolver<T> implements Resolver {
    @Override
    public int getOrder() {
        return 0;
    }

    abstract boolean support(T t);

}
