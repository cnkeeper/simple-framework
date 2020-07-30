package com.github.cnkeep.simple_springmvc.logger.support.slf4j;

import com.github.cnkeep.simple_springmvc.logger.Logger;

/**
 * 描述: slf4j适配
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
class Slf4jLogger implements Logger {
    private org.slf4j.Logger logger;

    Slf4jLogger(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void debug(String message, Object... arguments) {
        this.logger.debug(message, arguments);
    }

    @Override
    public void info(String mesage, Object... arguments) {
        this.logger.info(mesage, arguments);
    }

    @Override
    public void trace(String mesage, Object... arguments) {
        this.logger.warn(mesage, arguments);
    }

    @Override
    public void warn(String mesage, Object... arguments) {
        this.logger.warn(mesage, arguments);
    }

    @Override
    public void error(String mesage, Object... arguments) {
        this.logger.error(mesage, arguments);
    }
}
