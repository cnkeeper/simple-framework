package com.github.time69.simple_springmvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述:
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
public class ForwardView extends AbstractUrlView {
    public ForwardView() {
    }

    public ForwardView(String url, String contentType) {
        this.url = url;
        this.contentType = contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.getRequestDispatcher(url).forward(request,response);
    }
}
