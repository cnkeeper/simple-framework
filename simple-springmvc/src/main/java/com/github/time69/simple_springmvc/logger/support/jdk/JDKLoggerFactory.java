package com.github.time69.simple_springmvc.logger.support.jdk;

import com.github.time69.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.time69.simple_springmvc.logger.Logger;

/**
 * 描述:
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/28
 */
public class JDKLoggerFactory extends AbstractLoggerFactory {

    @Override
    protected Logger createLog(String name) {
        return  new JDKLogger(java.util.logging.Logger.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return createLog(clazz.getName());
    }
}
