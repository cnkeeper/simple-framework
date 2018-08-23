package com.github.time69.simple_framework;

import com.github.time69.simple_framework.annotation.Controller;
import com.github.time69.simple_framework.annotation.RequestMapping;
import com.github.time69.simple_framework.util.ClassUtils;
import com.github.time69.simple_framework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 描述: 前置控制器，所有被拦截的请求入口
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public class DispatcherServlet extends HttpServlet {
    private Map<String, HandlerMapping> handlerMappingMap;

    private static final String KEY_PACKAGENAMES = "packageNames";
    private static final String PACKAGE_SEPARATOR = ",";

    private static final String URL_SEPARATOR = "/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String packages = config.getInitParameter(KEY_PACKAGENAMES);
        initHandlerMapping(packages);
    }

    private void initHandlerMapping(String packages) {
        if (!StringUtils.isNotBlank(packages))
            throw new IllegalArgumentException("there is no packageNames found!");

        String[] packageNames = packages.split(PACKAGE_SEPARATOR);
        if (packageNames == null || packageNames.length < 1)
            throw new IllegalArgumentException("there is no packageNames found!");


        List<Class> classesHasAnnotation = new LinkedList<>();
        for (String pack : packageNames) {
            Class<?>[] classes = ClassUtils.scanPackage(pack);
            if (null == classes)
                continue;
            for (Class<?> clazz : classes) {
                if (null != clazz.getAnnotation(Controller.class)) {
                    scanClassMethod(clazz);
                }
            }
        }
    }

    /**
     * 扫描类中包含映射的方法
     *
     * @param clazz
     */
    private void scanClassMethod(Class<?> clazz) {
        String prefixMappingUrl = URL_SEPARATOR;
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        if (null != classMapping) {
            prefixMappingUrl = URL_SEPARATOR + classMapping.path().replaceAll(URL_SEPARATOR, "");
        }

        //不支持继承的方法，不支持非public的方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            RequestMapping requestMapping;
            if (Modifier.isPublic(method.getModifiers())
                    && null != (requestMapping = method.getAnnotation(RequestMapping.class))) {
                String url = prefixMappingUrl + URL_SEPARATOR + requestMapping.path().replaceAll(URL_SEPARATOR, "");


            }
            //排除非public方法
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
