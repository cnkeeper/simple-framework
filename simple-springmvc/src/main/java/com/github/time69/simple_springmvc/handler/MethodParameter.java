package com.github.time69.simple_springmvc.handler;

import lombok.Data;

/**
 * 描述: 参数映射实体类
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
@Data
public class MethodParameter {
    private Class<?> parameterType;
    private int index;
    private String name;
}
