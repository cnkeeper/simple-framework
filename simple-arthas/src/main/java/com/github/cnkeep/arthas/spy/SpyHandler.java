package com.github.cnkeep.arthas.spy;



import com.github.cnkeep.arthas.event.Event;
import com.github.cnkeep.arthas.event.EventListener;
import com.github.cnkeep.arthas.event.EventManager;

import java.util.Arrays;
/**
 * @description: 间谍回调函数
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class SpyHandler {
    /**
     * 符号	含义
     * $0, $1, $2, ...	this and 方法的参数
     * $args	方法参数数组.它的类型为 Object[]
     * $$	所有实参。例如, m($$) 等价于 m($1,$2,...)
     * $cflow(...)	cflow 变量
     * $r	    返回结果的类型，用于强制类型转换
     * $w	    包装器类型，用于强制类型转换
     * $_	    返回值
     * $sig	    类型为 java.lang.Class 的参数类型数组
     * $type	一个 java.lang.Class 对象，表示返回值类型
     * $class	一个 java.lang.Class 对象，表示当前正在修改的类
     * <p>
     * 链接：https://www.jianshu.com/p/b9b3ff0e1bf8
     */

    public static Object invoke(Class clazz, Object[] args, Class returnType, Object returnValue, String listenId) {
        StringBuilder builder = new StringBuilder();
        builder.append("Invoke SpyHandler class:").append(clazz).append("; ")
                .append("args:").append(Arrays.toString(args)).append("; ")
                .append("returnType:").append(returnType).append("; ")
                .append("returnValue:").append(returnValue).append(";  ")
                .append("listenId:").append(listenId);
        System.out.println(builder);

        Event event = new Event();
        event.setClazz(clazz);
        event.setArgs(args);
        event.setReturnType(returnType);
        event.setReturnValue(returnValue);
        event.setListenId(listenId);

        EventListener eventListener = EventManager.getInstance().getEventListener(listenId);
        if (eventListener != null) {
            return eventListener.onEvent(event);
        }
        return returnValue;
    }

    public static void logger(Class clazz, Object[] args, Class returnType, Object returnValue, Class r) {
        StringBuilder builder = new StringBuilder();
        builder.append("Invoke SpyHandler class:").append(clazz).append("; ")
                .append("args:").append(Arrays.toString(args)).append("; ")
                .append("returnType:").append(returnType).append("; ")
                .append("$r:").append(r).append("; ")
                .append("returnValue:").append(returnValue);
        System.out.println(builder);
    }

    public static void invokeException(Throwable e) {
        System.out.println(e);
    }
}
