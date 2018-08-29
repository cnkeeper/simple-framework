package com.github.time69.simple_springmvc.annotation;

import java.lang.annotation.*;

/**
 * 描述: 用于标识方法的返回值将不做视图解析的返回给前台
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseBody {
}
