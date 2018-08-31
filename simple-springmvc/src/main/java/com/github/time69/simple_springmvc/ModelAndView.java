package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.http.HttpStatus;
import lombok.Data;

import java.util.Map;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Data
public class ModelAndView {
    private View view;
    private Map<String,Object> modelMap;
    private HttpStatus status;
}
