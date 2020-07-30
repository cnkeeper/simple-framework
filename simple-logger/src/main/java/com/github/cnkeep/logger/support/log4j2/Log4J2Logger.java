package com.github.cnkeep.logger.support.log4j2;


import com.github.cnkeep.logger.Logger;

/**
 * 描述: log4j2适配
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
class Log4J2Logger implements Logger {
    private org.apache.logging.log4j.Logger logger;

    public Log4J2Logger(org.apache.logging.log4j.Logger logger) {
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
        this.logger.warn(mesage, arguments);
    }

    @Override
    public void error(String mesage, Object... arguments) {
        this.logger.error(mesage, arguments);
    }
}
