package com.github.cnkeep.unit;

import java.lang.annotation.*;

/**
 * @description: 方法名校验(方法名必须以Test结尾)
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodNameValidator {
}
