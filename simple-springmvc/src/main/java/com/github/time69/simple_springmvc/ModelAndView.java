package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.http.HttpStatus;
import lombok.Data;

import java.util.Map;

/**
 * 描述： 视图模型实体类，所有的请求返回值都会被包装为该对象，再交由后面的解析器处理
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Data
public class ModelAndView {
    private Object view;
    private Map<String, Object> modelMap;
    private HttpStatus status;

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return (view instanceof View) ? (View) view : null;
    }

    public String getViewName() {
        return (view instanceof String) ? (String) view : null;
    }

    /**
     * 是否需要映射到视图
     *
     * @return
     */
    public boolean isReference() {
        return (view instanceof String);
    }
}
