package com.github.time69.simple_springmvc.logger.support.logback;

import com.github.time69.simple_springmvc.logger.Logger;

/**
 * 描述: logback适配
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class LogBackLogger implements Logger {
    private ch.qos.logback.classic.Logger logger;

    public LogBackLogger(ch.qos.logback.classic.Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
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
        this.logger.trace(mesage, arguments);
    }

    @Override
    public void warn(String mesage, Object... arguments) {
        this.logger.trace(mesage, arguments);
    }

    @Override
    public void error(String mesage, Object... arguments) {
        this.logger.error(mesage, arguments);
    }
}
