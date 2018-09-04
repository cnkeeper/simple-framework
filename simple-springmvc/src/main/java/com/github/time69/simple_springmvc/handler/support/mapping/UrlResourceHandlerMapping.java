package com.github.time69.simple_springmvc.handler.support.mapping;

import com.github.time69.simple_springmvc.HandlerInterceptor;
import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;
import com.github.time69.simple_springmvc.handler.UrlResourceHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: for 静态资源
 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping, org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
public class UrlResourceHandlerMapping extends AbstractHandlerMapping{

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) {
        HandlerExecutionChain chain = new HandlerExecutionChain();
        UrlResourceHandler handler = getHandlerInternal(httpServletRequest);
        chain.setHandler(new UrlResourceHandler());
        for(HandlerInterceptor interceptor:handlerInterceptors){
            // if match
            chain.addInterceptor(interceptor);
        }
        return chain;
    }

    private UrlResourceHandler getHandlerInternal(HttpServletRequest httpServletRequest) {
        String contextPath = httpServletRequest.getContextPath();
        String pathInfo = httpServletRequest.getPathInfo();
        String requestURI = httpServletRequest.getRequestURI();
        String resourcePath = requestURI.replaceFirst(contextPath, "");


        return null;
    }
}
