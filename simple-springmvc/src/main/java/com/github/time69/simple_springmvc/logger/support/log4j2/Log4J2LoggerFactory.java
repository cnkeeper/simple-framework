package com.github.time69.simple_springmvc.logger.support.log4j2;

import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.AbstractLoggerFactory;
import org.apache.logging.log4j.LogManager;


/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class Log4J2LoggerFactory extends AbstractLoggerFactory {

    @Override
    protected Logger createLog(String name) {
        return new Log4J2Logger(LogManager.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return new Log4J2Logger(LogManager.getLogger(clazz));
    }
}
