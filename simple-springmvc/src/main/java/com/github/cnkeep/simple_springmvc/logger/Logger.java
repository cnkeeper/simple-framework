package com.github.cnkeep.simple_springmvc.logger;

/**
 * 描述: 集成外部日志的接口
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/27
 */
public interface Logger {
    /**
     * 返回日志对象名称
     * @return
     */
    String getName();

    /**
     * trace 等级是否开启
     * @return
     */
    boolean isTraceEnabled();

    /**
     * debug 等级是否开启
     * @return
     */
    boolean isDebugEnabled();


    /**
     * 打印 debug 级别日志
     *
     * @param message
     * @param arguments
     */
    void debug(String message, Object... arguments);


    /**
     * 打印 INFO 等级的日志
     *
     * @param mesage    日志信息
     * @param arguments
     */
    void info(String mesage, Object... arguments);

    /**
     * 打印 trace 等级的日志
     *
     * @param mesage    日志信息
     * @param arguments
     */
    void trace(String mesage, Object... arguments);

    /**
     * 打印 warn 等级的日志
     *
     * @param mesage    日志信息
     * @param arguments
     */
    void warn(String mesage, Object... arguments);

    /**
     * 打印 erros 等级的日志
     *
     * @param mesage    日志信息
     * @param arguments
     */
    void error(String mesage, Object... arguments);


}
