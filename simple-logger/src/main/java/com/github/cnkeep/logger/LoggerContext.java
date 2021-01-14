package com.github.cnkeep.logger;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public final class LoggerContext {
    private static LoggerFactory loggerFactory;

    private static final String[] LOG_IMPLS = {
            "org.slf4j.LoggerFactory=com.github.cnkeep.simple_springmvc.logger.support.slf4j.Slf4jLoggerFactory",
            "ch.qos.logback.classic.LoggerContext=com.github.cnkeep.simple_springmvc.logger.support.logback.LogBackLoggerFactory",
            "org.apache.logging.log4j.LogManager=com.github.cnkeep.simple_springmvc.logger.support.log4j2.Log4J2LoggerFactory",
            "org.apache.log4j.Logger=com.github.cnkeep.simple_springmvc.logger.support.log4j.Log4jLoggerFactory",
            "java.util.logging.Logger=com.github.cnkeep.simple_springmvc.logger.support.jdk.JdkLoggerFactory"
    };

    static {
        setLoggerImpl();
    }

    private static void setLoggerImpl() {
        for (int index = 0; index < LOG_IMPLS.length; index++) {
            try {
                Class.forName(LOG_IMPLS[index].split("=")[0]);
                loggerFactory = (LoggerFactory) Class.forName(LOG_IMPLS[index].split("=")[1]).newInstance();
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

    public static Logger getLog(String name) {
        return loggerFactory.getLog(name);
    }

    public static Logger getLog(Class<?> clazz) {
        return loggerFactory.getLog(clazz);
    }
}
