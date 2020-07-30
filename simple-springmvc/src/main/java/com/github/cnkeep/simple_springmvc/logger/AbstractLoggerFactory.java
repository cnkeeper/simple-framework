package com.github.cnkeep.simple_springmvc.logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public abstract class AbstractLoggerFactory implements LoggerFactory {
    protected static ConcurrentMap<String, Logger> LOGGER_CACHE;

    static {
        LOGGER_CACHE = new ConcurrentHashMap<String, Logger>();
    }

    @Override
    public Logger getLog(String name) {
        if (null == name) {
            throw new IllegalArgumentException("name should not be null!");
        }

        Logger logger = LOGGER_CACHE.get(name);
        if (logger == null) {
            logger = createLog(name);
            LOGGER_CACHE.put(logger.getName(), logger);
        }
        return logger;
    }


    @Override
    public Logger getLog(Class<?> clazz) {
        if (null == clazz.getName()) {
            throw new IllegalArgumentException("name should not be null!");
        }

        Logger logger = LOGGER_CACHE.get(clazz.getName());
        if (logger == null) {
            logger = createLog(clazz);
            LOGGER_CACHE.put(logger.getName(), logger);
        }
        return logger;
    }

    /**
     * 通过名称获取日志对象
     * @param name
     * @return
     */
    protected abstract Logger createLog(String name);

    /**
     * 通过class获取日志对象
     * @param clazz
     * @return
     */
    protected abstract Logger createLog(Class<?> clazz);
}
