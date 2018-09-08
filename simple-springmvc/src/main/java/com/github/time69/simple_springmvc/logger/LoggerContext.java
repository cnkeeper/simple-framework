package com.github.time69.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public final class LoggerContext {
    private static LoggerFactory loggerFactory;

    private static final String[] LOG_IMPLS = {
        /*    "org.slf4j.LoggerFactory=Slf4jLoggerFactory",
            "ch.qos.logback.classic.LoggerContext=LogBackLoggerFactory",*/
            "org.apache.logging.log4j.LogManager=Log4J2LoggerFactory",
            "org.apache.log4j.Logger=Log4jLoggerFactory",
            "java.util.logging.Logger=JdkLoggerFactory"
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
