package com.github.time69.simple_springmvc.resolver;

import com.github.time69.simple_springmvc.View;
import com.github.time69.simple_springmvc.ViewResolver;

/**
 * 描述: 静态资源解析器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public class StaticResourceViewResolver implements ViewResolver{
    @Override
    public View resolveView(String viewName) {

        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
