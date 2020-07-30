package com.github.cnkeep.simple_springmvc.view;

import com.github.cnkeep.simple_springmvc.View;
import lombok.Data;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
@Data
public abstract class AbstractUrlView implements View {
    protected String url;
    protected String contentType;
}
