package com.github.cnkeep.simple_springmvc.annotation;

import java.lang.annotation.*;

/**
 * 描述: param参数
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestParam {
    /**
     * 参数映射key
     * @return
     */
    String name();

    /**
     * 参数是否必须
     * @return
     */
    boolean required() default true;
}
