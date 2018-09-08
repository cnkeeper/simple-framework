package com.github.time69.simple_springmvc.annotation;

import java.lang.annotation.*;

/**
 * url中参数的注解
 */
@Target(ElementType.PARAMETER)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface PathParam {
    String name() default "";

    /**
     * required=false, 时未找到匹配项时默认值
     *
     * @return
     */
    String defalutValue() default "";

    /**
     * 参数是否必须
     *
     * @return
     */
    boolean required() default true;
}
