package com.github.cnkeep.simple_springmvc.resolver.view;

import com.github.cnkeep.simple_springmvc.Resolver;
import com.github.cnkeep.simple_springmvc.View;

/**
 * 描述: 资源解析器, 通过资源名查找对应的资源
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface ViewResolver extends Resolver {
    /**
     * 通过名称解析资源
     * @param viewName
     * @return
     */
    View resolveView(String viewName);
}
