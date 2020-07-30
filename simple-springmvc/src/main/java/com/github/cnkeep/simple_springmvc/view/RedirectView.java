package com.github.cnkeep.simple_springmvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 描述: 重定向view
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/2
 */
public class RedirectView extends AbstractUrlView {

    public RedirectView(String url, String contentType) {
        this.url = url;
        this.contentType = contentType;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        sendRedirect(request, response, model);
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, Map<String, ?> model) throws IOException {
        response.sendRedirect(url);
    }
}
