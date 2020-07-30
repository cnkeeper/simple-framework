package com.github.cnkeep.simple_springmvc.logger.support.jdk;

import com.github.cnkeep.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.cnkeep.simple_springmvc.logger.Logger;

/**
 * 描述:
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class JdkLoggerFactory extends AbstractLoggerFactory {

    @Override
    protected Logger createLog(String name) {
        return  new JdkLogger(java.util.logging.Logger.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return createLog(clazz.getName());
    }
}
