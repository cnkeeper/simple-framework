package com.github.time69.simple_springmvc.resolver.view;

import com.github.time69.simple_springmvc.View;
import com.github.time69.simple_springmvc.view.ForwardView;
import com.github.time69.simple_springmvc.view.RedirectView;
import lombok.Data;

import java.io.File;
import java.net.URL;

/**
 * 描述: 静态资源解析器
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
@Data
public class UrlResourceViewResolver implements ViewResolver {
    /**静态资源目录*/
    public static final String[] DEFAULT_STATIC_PATH = {"resources", "public", "static"};

    /**静态资源的前缀路径*/
    public static final String DEFAULT_VIEW_PREFIX = "";

    /**静态资源的后缀名*/
    public static final String DEFAULT_VIEW_STUFF = ".html";

    private String[] staticPath = DEFAULT_STATIC_PATH;

    private String prefix = DEFAULT_VIEW_PREFIX;

    private String stuff = DEFAULT_VIEW_STUFF;

    @Override
    public View resolveView(String viewName) {
        View view = null;

        // 在静态资源目录中查找视图
        String path = prefix + File.separator+viewName + stuff;
        String lookPath = lookupPath(path);
        if (null == lookPath) {
            return view;
        }

        String contentType = resolveContentType(lookPath);
        if (viewName.startsWith(View.PREFIX_FORWARD)) {
            view = new ForwardView(lookPath, contentType);
        } else if (viewName.startsWith(View.PREFIX_REDIRECT)) {
            view = new RedirectView(lookPath, contentType);
        }

        return view;
    }

    private String resolveContentType(String lookPath) {
        return null;
    }

    /**
     * TODO 在静态资源目录中查找视图
     *
     * @param path
     * @return
     */
    public String lookupPath(String path) {
        for (String p : staticPath) {
            URL resource = this.getClass().getClassLoader().getResource(path);
            if (resource != null) {
                return path;
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
