package com.github.time69.simple_springmvc.handler;

import com.github.time69.simple_springmvc.Handler;
import com.github.time69.simple_springmvc.HandlerMapping;
import com.github.time69.simple_springmvc.http.MediaType;
import com.github.time69.simple_springmvc.resolver.view.UrlResourceViewResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 描述: 静态资源处理器
 * @see org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
 * @see org.springframework.web.servlet.handler.SimpleUrlHandlerMapping
 * @see org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter
 * @see org.springframework.web.servlet.resource.ResourceHttpRequestHandler
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/3
 */
public class UrlResourceHandler implements Handler {

    private UrlResourceViewResolver viewResolver = new UrlResourceViewResolver();

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        if(requestPath == null){
            // 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String path = viewResolver.lookupPath(requestPath);

        MediaType contentType = resolveContentType(path);
        response.setContentType(contentType.getContentType());

        URL resource = null;
        for (String p : UrlResourceViewResolver.DEFAULT_STATIC_PATH) {
            resource = this.getClass().getClassLoader().getResource(p+ File.separator+path.replaceFirst("/",""));
            if (resource != null) {
                break;
            }
        }
        ServletOutputStream outputStream = response.getOutputStream();
        if(null == request){
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }else {
            try(InputStream inputStream = resource.openStream()){
                byte[] buffer = new byte[1024];
                int len = 0;
                while (-1 != (len = inputStream.read(buffer))){
                    outputStream.write(buffer,0,len);
                }
                outputStream.flush();
            }catch (Exception e){
                response.sendError(HttpServletResponse.SC_BAD_GATEWAY);
            }
        }

    }

    private MediaType resolveContentType(String path) {
        int index = path.lastIndexOf(".");
        if(index<1){
            return MediaType.ALL;
        }

        return MediaType.TEXT_HTML;
    }
}
