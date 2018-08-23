package com.github.time69.simple_framework.annotation;

import java.lang.annotation.*;

/**
 * 描述: param参数
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    String name() default "";
}
