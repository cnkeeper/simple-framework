package com.github.time69.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public interface LoggerFactory {

    Logger getLog(String name);

    Logger getLog(Class<?> clazz);
}
