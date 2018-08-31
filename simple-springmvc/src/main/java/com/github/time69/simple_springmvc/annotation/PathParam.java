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
}
