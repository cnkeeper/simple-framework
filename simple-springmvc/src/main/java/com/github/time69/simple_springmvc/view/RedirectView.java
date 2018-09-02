package com.github.time69.simple_springmvc.view;

import com.github.time69.simple_springmvc.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述: 重定向view
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedirectView implements View {
    private String url;

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        sendRedirect(request, response, model);
    }

    private void sendRedirect(HttpServletRequest request, HttpServletResponse response, Map<String, ?> model) {
    }
}