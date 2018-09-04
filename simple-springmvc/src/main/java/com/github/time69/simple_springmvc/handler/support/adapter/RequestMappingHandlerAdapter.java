package com.github.time69.simple_springmvc.handler.support.adapter;

import com.github.time69.simple_springmvc.Handler;
import com.github.time69.simple_springmvc.HandlerAdapter;
import com.github.time69.simple_springmvc.ModelAndView;
import com.github.time69.simple_springmvc.handler.MethodHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Handler handler) {
        return (handler instanceof MethodHandler);
    }

    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws InvocationTargetException, IllegalAccessException {
        ModelAndView modelAndView = null;

        bindingArguments(request, handler);
        modelAndView = invokeMethod(request, handler);

        return modelAndView;
    }


    /**
     * 请求参数封装
     *
     * @param request
     * @param handler
     */
    private void bindingArguments(HttpServletRequest request, Handler handler) {

    }

    private ModelAndView invokeMethod(HttpServletRequest request, Handler handler) throws InvocationTargetException, IllegalAccessException {
        ModelAndView modelAndView = null;
        if (handler instanceof MethodHandler) {
            MethodHandler methodHandler = (MethodHandler) handler;
            Method method = methodHandler.getMethod();
            Object beanInstance = methodHandler.getBeanInstance();

            // TODO 自动封装参数
            Object[] parameters = getMethodArgumentValues(request,handler);
            Object returnValue = method.invoke(beanInstance, parameters);
            // TODO 函数返回值构建为ModelAndView
            if(returnValue instanceof ModelAndView){
                modelAndView = (ModelAndView) returnValue;
            }else if(returnValue instanceof String){
                modelAndView = new ModelAndView();
            }
        }
        return modelAndView;
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request, Handler handler) {
        // resolverMethodArguments
        return null;
    }


}
