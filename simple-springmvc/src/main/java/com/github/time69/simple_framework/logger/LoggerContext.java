package com.github.time69.simple_framework.logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public final class LoggerContext {
    private static LoggerFactory loggerFactory;

    private static final String[] log_impls = {
        /*    "org.slf4j.LoggerFactory=com.github.time69.simple_framework.logger.support.slf4j.SLF4JLoggerFactory",
            "ch.qos.logback.classic.LoggerContext=com.github.time69.simple_framework.logger.support.logback.LogBackLoggerFactory",*/
            "org.apache.logging.log4j.LogManager=com.github.time69.simple_framework.logger.support.log4j2.Log4J2LoggerFactory",
            "org.apache.log4j.Logger=com.github.time69.simple_framework.logger.support.log4j.Log4JLoggerFactory",
            "java.util.logging.Logger=com.github.time69.simple_framework.logger.support.jdk.JDKLoggerFactory"
    };

    static {
        setLoggerImpl();
    }

    private static void setLoggerImpl() {
        for (int index = 0; index < log_impls.length; index++) {
            try {
                Class.forName(log_impls[index].split("=")[0]);
                loggerFactory = (LoggerFactory) Class.forName(log_impls[index].split("=")[1]).newInstance();
                return;
            } catch (Exception e) {
                // ignore
                e.printStackTrace();
            }
        }
    }

    public static LoggerFactory getLoggerFactory() {
        return loggerFactory;
    }
}
