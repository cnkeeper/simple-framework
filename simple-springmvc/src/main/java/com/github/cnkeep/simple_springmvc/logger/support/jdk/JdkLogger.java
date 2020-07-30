package com.github.cnkeep.simple_springmvc.logger.support.jdk;

import com.github.cnkeep.simple_springmvc.logger.Logger;

import java.util.logging.Level;

/**
 * 描述: jdk logger适配
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
class JdkLogger implements Logger {

    private java.util.logging.Logger logger;

    public JdkLogger(java.util.logging.Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isLoggable(Level.ALL);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isLoggable(Level.FINE);
    }

    @Override
    public void debug(String message, Object... arguments) {
        this.logger.logp(Level.FINE, getName(), null, String.format(message, arguments));
    }

    @Override
    public void info(String mesage, Object... arguments) {
        this.logger.logp(Level.INFO, getName(), null, String.format(mesage, arguments));
    }

    @Override
    public void trace(String mesage, Object... arguments) {
        // nothing to do!
    }

    @Override
    public void warn(String mesage, Object... arguments) {
        logger.logp(Level.WARNING, getName(), null, String.format(mesage, arguments));
    }

    @Override
    public void error(String mesage, Object... arguments) {
        logger.logp(Level.SEVERE, getName(), null, String.format(mesage, arguments));
    }
}
