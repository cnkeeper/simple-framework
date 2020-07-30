package com.github.cnkeep.simple_springmvc.logger.support.logback;

import ch.qos.logback.classic.LoggerContext;
import com.github.cnkeep.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.cnkeep.simple_springmvc.logger.Logger;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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
