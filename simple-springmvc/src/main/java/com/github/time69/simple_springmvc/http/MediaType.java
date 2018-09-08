package com.github.time69.simple_springmvc.http;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
public enum MediaType {
    /**
     * 所有格式
     */
    ALL("*/*"),

    /**
     * application/json;charset=UTF-8
     */
    APPLICATION_JSON_UTF8("application/json;charset=UTF-8"),

    /**
     * text/html
     */
    TEXT_HTML("text/html"),

    /**
     * text/xml
     */
    TEXT_XML("text/xml"),

    /**
     * image/png
     */
    IMAGE_PNG("image/png"),

    /**
     * image/gif
     */
    IMAGE_GIF("image/gif"),

    /**
     * image/jpeg
     */
    IMAGE_JPEG("image/jpeg");

    private String contentType;

    private MediaType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}
