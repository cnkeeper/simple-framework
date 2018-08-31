package com.github.time69.simple_springmvc.handler;

import com.github.time69.simple_springmvc.HandlerInterceptor;
import com.github.time69.simple_springmvc.context.ApplicationContext;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Data
public class RequestMappingHandlerMapping extends AbstractHandlerMapping {

    private Map<String, HandlerMethod> handlerMethodMap;
    private HandlerInterceptor[] handlerInterceptors;

    public RequestMappingHandlerMapping() {
        this.handlerMethodMap = ApplicationContext.BEAN_HANDLER_METHOD_MAP;
    }

    @Override
    public HandlerExecutionChain getHander(HttpServletRequest httpServletRequest) {
        HandlerMethod handler = getHandlerMethod(httpServletRequest);
        HandlerExecutionChain chain = new HandlerExecutionChain();
        chain.setHandler(handler);
        for(HandlerInterceptor interceptor:handlerInterceptors){
            // if match
            chain.addInterceptor(interceptor);
        }
        return chain;
    }

    private HandlerMethod getHandlerMethod(HttpServletRequest httpServletRequest) {
        String pathInfo = httpServletRequest.getPathInfo();
        return this.handlerMethodMap.get(pathInfo);
    }

}
