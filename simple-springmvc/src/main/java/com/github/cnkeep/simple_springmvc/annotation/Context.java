package com.github.cnkeep.simple_springmvc.annotation;

import java.lang.annotation.*;

/**
 * 描述: 用于标识注入Request, Response对象
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target(ElementType.PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Context {
}
