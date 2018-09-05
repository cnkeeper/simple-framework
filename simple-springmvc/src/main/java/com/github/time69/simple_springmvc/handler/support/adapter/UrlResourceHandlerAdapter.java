package com.github.time69.simple_springmvc.handler.support.adapter;

import com.github.time69.simple_springmvc.Handler;
import com.github.time69.simple_springmvc.HandlerAdapter;
import com.github.time69.simple_springmvc.ModelAndView;
import com.github.time69.simple_springmvc.handler.UrlResourceHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

/**
 * 描述: 静态资源请求执行器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/4
 */
public class UrlResourceHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Handler handler) {
        return (handler instanceof UrlResourceHandler);
    }

    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws InvocationTargetException, IllegalAccessException {
        //静态资源直接处理返回响应
        ((UrlResourceHandler)handler).handle(request,response);
        return null;
    }
}
