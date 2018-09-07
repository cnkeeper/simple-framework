package com.github.time69.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public interface LoggerFactory {

    /**
     * get logger by name
     * @param name
     * @return
     */
    Logger getLog(String name);

    /**
     * get logger by class
     * @param clazz
     * @return
     */
    Logger getLog(Class<?> clazz);
}
