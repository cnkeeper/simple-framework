package com.github.time69.simple_springmvc.http;

import lombok.Data;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public enum HttpStatus {
    status_200(200, "OK"),
    status_403(403,"403"),
    status_404(404, "404"),
    status_500(500, "500");
    private int status;
    private String desc;

    private HttpStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
