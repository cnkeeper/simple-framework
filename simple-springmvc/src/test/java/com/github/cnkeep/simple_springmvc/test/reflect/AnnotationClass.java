package com.github.cnkeep.simple_springmvc.test.reflect;

import com.github.cnkeep.simple_springmvc.annotation.RequestParam;
import com.github.cnkeep.simple_springmvc.annotation.ResponseBody;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/9
 */
public class AnnotationClass {
    @ResponseBody
    public int add(@RequestParam(name = "num1", required = true) int num1, @RequestParam(name = "num2") int num2) {
        return num1 + num2;
    }
}
