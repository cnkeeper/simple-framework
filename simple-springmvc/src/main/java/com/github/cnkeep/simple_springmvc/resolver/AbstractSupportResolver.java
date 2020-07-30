package com.github.cnkeep.simple_springmvc.resolver;

import com.github.cnkeep.simple_springmvc.Resolver;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public abstract class AbstractSupportResolver<T> implements Resolver {
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 当前解析器是否支持解析
     * @param t
     * @return
     */
    abstract boolean support(T t);

}
