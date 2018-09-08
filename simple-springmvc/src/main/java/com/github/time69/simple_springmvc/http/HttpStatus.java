package com.github.time69.simple_springmvc.http;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public enum HttpStatus {

    /**
     * response status:200
     */
    STATUS_200(200, "OK"),

    /**
     * response status:200
     */
    STATUS_403(403,"403"),
    /**
     * response status:200
     */
    STATUS_404(404, "404"),
    /**
     * response status:200
     */
    STATUS_500(500, "500");

    private int status;
    private String desc;

    private HttpStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
