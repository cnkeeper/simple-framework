package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述: 根据请求的不同类型，返回相应的Handler处理器<br/>
 *  <ul>
 *      <li>方法处理器：MethodHandler</li>
 *      <li>静态资源处理器：ResourceHttpRequestHandler</li>
 *  </ul>
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface HandlerMapping {
    String PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE = HandlerMapping.class.getName() + ".pathWithinHandlerMapping";

    HandlerExecutionChain getHandler(HttpServletRequest httpServletRequest);
}
