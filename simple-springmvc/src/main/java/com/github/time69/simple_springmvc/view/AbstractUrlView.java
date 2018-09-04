package com.github.time69.simple_springmvc.view;

import com.github.time69.simple_springmvc.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
@Data
public abstract class AbstractUrlView implements View{
    protected String url;
    protected String contentType;
}
