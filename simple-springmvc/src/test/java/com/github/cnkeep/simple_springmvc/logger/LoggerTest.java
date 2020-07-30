package com.github.cnkeep.simple_springmvc.logger;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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
