package com.github.time69.simple_springmvc.test.reflect;

import com.github.time69.simple_springmvc.annotation.RequestParam;
import com.github.time69.simple_springmvc.annotation.ResponseBody;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/9
 */
public class AnnotationClass {
    @ResponseBody
    public int add(@RequestParam(name = "num1", required = true) int num1, @RequestParam(name = "num2") int num2) {
        return num1 + num2;
    }
}
