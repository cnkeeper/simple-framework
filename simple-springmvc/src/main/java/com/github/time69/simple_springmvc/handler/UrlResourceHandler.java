package com.github.time69.simple_springmvc.handler;

import com.github.time69.simple_springmvc.Handler;
import com.github.time69.simple_springmvc.HandlerMapping;
import com.github.time69.simple_springmvc.resolver.UrlResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 描述: 静态资源处理器
 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
 * @see org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
 * @see org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
 * @see org.springframework.web.servlet.resource.ResourceHttpRequestHandler
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
public class UrlResourceHandler implements Handler {

    private UrlResourceViewResolver viewResolver = new UrlResourceViewResolver();

    public void handle(HttpServletRequest request, HttpServletResponse response){
        String requestPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        if(requestPath == null){

        }

    }
}
