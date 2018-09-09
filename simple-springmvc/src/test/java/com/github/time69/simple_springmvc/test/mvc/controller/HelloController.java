package com.github.time69.simple_springmvc.test.mvc.controller;

import com.github.time69.simple_springmvc.annotation.*;
import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.LoggerContext;
import com.github.time69.simple_springmvc.test.mvc.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
    private static final Logger LOGGER = LoggerContext.getLog(HelloController.class);

    @RequestMapping(path = "/html")
    public String html() {
        return "/test";
    }

    @RequestMapping(path = "/json")
    @ResponseBody
    public User json() {
        return new User("1001", "zhang三", 12);
    }

    @RequestMapping(path = "/param")
    @ResponseBody
    public User param(@RequestParam(name = "name") Map<String, String[]> params, @Context HttpServletRequest request) {
        LOGGER.info("params={}", params);
        LOGGER.info("request={}", request);
        String[] strings = params.get("user.name");
        if (null != strings && strings.length == 1) {
            return new User("1001", strings[0], 12);
        }else {
            return new User();
        }
    }
}
