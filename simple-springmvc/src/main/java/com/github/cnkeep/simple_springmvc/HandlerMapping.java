package com.github.cnkeep.simple_springmvc;

import com.github.cnkeep.simple_springmvc.handler.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 根据请求的不同类型，返回相应的Handler处理器<br/>
 *  <ul>
 *      <li>方法处理器：MethodHandler</li>
 *      <li>静态资源处理器：ResourceHttpRequestHandler</li>
 *  </ul>
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface HandlerMapping {
    String PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE = HandlerMapping.class.getName() + ".pathWithinHandlerMapping";

    /**
     * 查找处理器
     * @param httpServletRequest
     * @return
     */
    HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest);
}
