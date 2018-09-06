package com.github.time69.simple_springmvc.resolver.view;

import com.github.time69.simple_springmvc.Resolver;
import com.github.time69.simple_springmvc.View;

/**
 * 描述: 资源解析器, 通过资源名查找对应的资源
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface ViewResolver extends Resolver {
    View resolveView(String viewName);
}
