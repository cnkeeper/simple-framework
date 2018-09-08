package com.github.time69.simple_springmvc.logger.support.slf4j;

import com.github.time69.simple_springmvc.logger.AbstractLoggerFactory;
import com.github.time69.simple_springmvc.logger.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述: slf4j工厂类
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public class Slf4jLoggerFactory extends AbstractLoggerFactory {

    @Override
    protected Logger createLog(String name) {
        return new Slf4jLogger(LoggerFactory.getLogger(name));
    }

    @Override
    protected Logger createLog(Class<?> clazz) {
        return new Slf4jLogger(LoggerFactory.getLogger(clazz));
    }
}
