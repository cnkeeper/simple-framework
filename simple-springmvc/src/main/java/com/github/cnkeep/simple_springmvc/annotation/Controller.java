package com.github.cnkeep.simple_springmvc.annotation;

import java.lang.annotation.*;

/**
 * 描述: Controller标识
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
