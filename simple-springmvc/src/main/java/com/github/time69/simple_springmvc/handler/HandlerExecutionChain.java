package com.github.time69.simple_springmvc.handler;

import com.github.time69.simple_springmvc.Handler;
import com.github.time69.simple_springmvc.HandlerInterceptor;
import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.LoggerContext;
import com.github.time69.simple_springmvc.ModelAndView;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * 描述: 拦截器链
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Data
public class HandlerExecutionChain {
    private static Logger LOGGER = LoggerContext.getLog(HandlerExecutionChain.class);
    //实际处理的对象，其实就是controller中的method
    private Handler handler;
    private List<HandlerInterceptor> handlerInterceptors = Collections.emptyList();

    public void addInterceptor(HandlerInterceptor interceptor) {
        this.handlerInterceptors.add(interceptor);
    }

    public boolean applyPreHandler(HttpServletRequest request, HttpServletResponse response) {
        boolean preResult = true;
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            try {
                preResult = interceptor.preHandler(request, response);
            } catch (Exception e) {
                LOGGER.error("Exception in executing interceptor[{}], cause by{}", interceptor, e);
                preResult = false;
            }
            if (!preResult)
                return false;
        }

        return preResult;
    }

    public void applyPostHandler(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView) {
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            try {
                interceptor.postHandler(request, response, modelAndView);
            } catch (Exception e) {
                LOGGER.error("Exception in executing interceptor[{}], cause by{}", interceptor, e);
                return;
            }
        }
    }
}
