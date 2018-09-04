package com.github.time69.simple_springmvc.handler.support.mapping;

import com.github.time69.simple_springmvc.HandlerInterceptor;
import com.github.time69.simple_springmvc.context.ApplicationContext;
import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;
import com.github.time69.simple_springmvc.handler.MethodHandler;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述: for RequestMapping
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Data
public class RequestMappingHandlerMapping extends AbstractHandlerMapping {

    private Map<String, MethodHandler> handlerMethodMap;

    public RequestMappingHandlerMapping() {
        this.handlerMethodMap = ApplicationContext.BEAN_HANDLER_METHOD_MAP;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest) {
        MethodHandler handler = getHandlerMethod(httpServletRequest);
        if (null == handler)
            return null;

        HandlerExecutionChain chain = new HandlerExecutionChain();
        chain.setHandler(handler);
        for (HandlerInterceptor interceptor : handlerInterceptors) {
            // if match
            chain.addInterceptor(interceptor);
        }
        return chain;
    }

    private MethodHandler getHandlerMethod(HttpServletRequest httpServletRequest) {
        String pathInfo = httpServletRequest.getPathInfo();
        return this.handlerMethodMap.get(pathInfo);
    }

}
