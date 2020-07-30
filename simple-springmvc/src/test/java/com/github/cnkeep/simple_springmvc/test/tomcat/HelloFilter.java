package com.github.cnkeep.simple_springmvc.test.tomcat;

import javax.servlet.*;
import java.io.IOException;

/**
 * 描述: hello Filter
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/22
 */
public class HelloFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init.....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("doFilter.....");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destroy.....");
    }
}
