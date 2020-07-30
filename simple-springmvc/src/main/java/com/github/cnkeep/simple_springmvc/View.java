package com.github.cnkeep.simple_springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 描述： 数据模型渲染视图
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public interface View {
    String PREFIX_REDIRECT = "redirect";
    String PREFIX_FORWARD = "forward";

    /**
     * 返回视图的内容类型
     *
     * @return
     */
    String getContentType();

    /**
     * 使用数据模型渲染视图
     *
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
