package com.github.cnkeep.simple_springmvc.handler.support.adapter;

import com.github.cnkeep.simple_springmvc.Handler;
import com.github.cnkeep.simple_springmvc.HandlerAdapter;
import com.github.cnkeep.simple_springmvc.ModelAndView;
import com.github.cnkeep.simple_springmvc.handler.UrlResourceHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 描述: 静态资源请求执行器
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/4
 */
public class UrlResourceHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Handler handler) {
        return (handler instanceof UrlResourceHandler);
    }

    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws InvocationTargetException, IllegalAccessException, IOException {
        //静态资源直接处理返回响应
        ((UrlResourceHandler)handler).handle(request,response);
        return null;
    }
}
