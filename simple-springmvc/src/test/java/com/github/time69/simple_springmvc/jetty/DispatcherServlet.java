package com.github.time69.simple_springmvc.jetty;

import sun.reflect.Reflection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/22
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        writer.println("<html><title>Welcome Jetty</title><body>");
        writer.println("<h1>Hello World!</h1>");
        writer.println("<p>dispatcher servlet</p>");
        writer.println("</body></html>");
        getRequestPath(req);
    }

    private String getRequestPath(HttpServletRequest req){
        String path = "/";
        String contextPath = req.getContextPath();
        System.out.println("contextPath: "+contextPath);

        String pathInfo = req.getPathInfo();
        System.out.println("pathInfo: "+pathInfo);

        String requestURI = req.getRequestURI();
        System.out.println("requestURI: "+requestURI);

        String servletPath = req.getServletPath();
        System.out.println("servletPath: "+servletPath);

        System.out.println(Reflection.getCallerClass());
        return path;
    }
}
