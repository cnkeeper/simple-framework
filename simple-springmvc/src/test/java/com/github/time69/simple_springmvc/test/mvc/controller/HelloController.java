package com.github.time69.simple_springmvc.test.mvc.controller;

import com.github.time69.simple_springmvc.annotation.Controller;
import com.github.time69.simple_springmvc.annotation.RequestMapping;
import com.github.time69.simple_springmvc.annotation.ResponseBody;
import com.github.time69.simple_springmvc.test.mvc.entity.User;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Controller
@RequestMapping(path = "/test")
public class HelloController {
    @RequestMapping(path = "/html")
    public String html() {
        return "/test";
    }

    @RequestMapping(path = "/json")
    @ResponseBody
    public User json() {
        return new User("1001","zhang三",12);
    }
}
