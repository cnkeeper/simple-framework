package com.github.time69.simple_framework.logger.support.logback;

import ch.qos.logback.classic.LoggerContext;
import com.github.time69.simple_framework.logger.AbstractLoggerFactory;
import com.github.time69.simple_framework.logger.Logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class LogBackLoggerFactory extends AbstractLoggerFactory {
    private final LoggerContext loggerContext = new LoggerContext();

    @Override
    protected Logger createLog(String name) {
        return new LogBackLogger(loggerContext.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return new LogBackLogger(loggerContext.getLogger(clazz));
    }
}
