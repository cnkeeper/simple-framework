package com.github.time69.simple_springmvc.test.mvc.controller;

import com.github.time69.simple_springmvc.annotation.Controller;
import com.github.time69.simple_springmvc.annotation.RequestMapping;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Controller
@RequestMapping(path = "/hello")
public class HelloController {
    @RequestMapping(path = "/test")
    public String test() {
        return "hello";
    }
}
