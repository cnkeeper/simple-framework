package com.github.time69.simple_framework.logger.support.slf4j;

import com.github.time69.simple_framework.logger.Logger;

/**
 * 描述: slf4j适配
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
class SLF4JLogger implements Logger {
    private org.slf4j.Logger logger;

    SLF4JLogger(org.slf4j.Logger logger) {
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
