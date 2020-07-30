package com.github.cnkeep.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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
