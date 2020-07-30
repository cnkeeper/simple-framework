package com.github.cnkeep.simple_springmvc.test.mvc;

import com.github.cnkeep.simple_springmvc.DispatcherServlet;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Enumeration;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
public class DispatcherServletTest {
    @Test
    public void testInit() throws ServletException {
        ServletConfig servletConfig = new ServletConfig() {
            @Override
            public String getServletName() {
                return null;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public String getInitParameter(String s) {
                return "packageNames".equals(s)?"com.github.time69.simple_springmvc.test.mvc.controller":"";
            }

            @Override
            public Enumeration<String> getInitParameterNames() {
                return null;
            }
        };

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.init(servletConfig);
    }

}
