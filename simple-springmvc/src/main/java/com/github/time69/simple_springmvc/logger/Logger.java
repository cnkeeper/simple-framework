package com.github.time69.simple_springmvc.logger;

/**
 * 描述: 集成外部日志的接口
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public interface Logger {
    String getName();

    /**
     * @return trace 等级是否开启
     */
    boolean isTraceEnabled();

    /**
     * @return debug 等级是否开启
     */
    boolean isDebugEnabled();


    /**
     * 打印 debug 级别日志
     *
     * @param message
     */
    void debug(String message, Object... arguments);


    /**
     * 打印 INFO 等级的日志
     *
     * @param mesage 日志信息
     */
    void info(String mesage, Object... arguments);

    /**
     * 打印 trace 等级的日志
     *
     * @param mesage 日志信息
     */
    void trace(String mesage, Object... arguments);

    /**
     * 打印 warn 等级的日志
     *
     * @param mesage 日志信息
     */
    void warn(String mesage, Object... arguments);

    /**
     * 打印 erros 等级的日志
     *
     * @param mesage 日志信息
     */
    void error(String mesage, Object... arguments);


}
