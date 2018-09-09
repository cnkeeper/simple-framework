package com.github.time69.simple_springmvc.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Parameter;

/**
 * 描述: 参数映射实体类
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MethodParameter {
    private Class<?> parameterType;

    private Parameter parameter;
    /**
     * 参数下标
     */
    private int index;
    /**
     * 参数名
     */
    private String name;
}
