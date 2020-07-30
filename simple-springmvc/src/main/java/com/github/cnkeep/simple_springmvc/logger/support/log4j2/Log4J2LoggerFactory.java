package com.github.cnkeep.simple_springmvc.logger.support.log4j2;

import com.github.cnkeep.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.cnkeep.simple_springmvc.logger.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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
