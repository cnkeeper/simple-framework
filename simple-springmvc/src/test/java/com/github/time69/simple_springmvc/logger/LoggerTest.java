package com.github.time69.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class LoggerTest {

    public static void main(String[] args) {
        LoggerFactory loggerFactory = LoggerContext.getLoggerFactory();
        System.out.println(loggerFactory);

        Logger log = loggerFactory.getLog(LoggerTest.class);
        System.out.println(log);

        log.error("hello {}","zhangsan ");
    }

}
