package com.github.cnkeep.simple_springmvc.logger.support.log4j;

import com.github.cnkeep.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.cnkeep.simple_springmvc.logger.Logger;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public class Log4jLoggerFactory extends AbstractLoggerFactory {

    @Override
    protected Logger createLog(String name) {
        return new Log4jLogger(org.apache.log4j.Logger.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return new Log4jLogger(org.apache.log4j.Logger.getLogger(clazz));
    }
}
