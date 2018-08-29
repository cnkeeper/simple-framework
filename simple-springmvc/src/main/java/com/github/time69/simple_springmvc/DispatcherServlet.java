package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.util.ClassScan;
import com.github.time69.simple_springmvc.util.StringUtils;
import com.github.time69.simple_springmvc.annotation.Controller;
import com.github.time69.simple_springmvc.annotation.RequestMapping;
import com.github.time69.simple_springmvc.handler.HandlerMethod;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 描述: 前置控制器，所有被拦截的请求入口
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public class DispatcherServlet extends HttpServlet {
    private Map<String, HandlerMapping> handlerMappingMap;
    private List<HandlerAdapter> handlerAdapters;
    private Map<String, HandlerMethod> handlerMethodMap;

    private static final String KEY_PACKAGENAMES = "packageNames";
    private static final String PACKAGE_SEPARATOR = ",";

    private static final String URL_SEPARATOR = "/";

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String packages = config.getInitParameter(KEY_PACKAGENAMES);
        initHandlerMapping(packages);
        initHandlerAdapter();
        initHandlerMethod(packages);
        System.out.println(this.handlerMethodMap);
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispater(req, resp);
        } catch (Exception e) {

        }

        super.service(req, resp);
    }

    private void doDispater(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    /**
     * 在加载该servlet时初始化HeadlerMapping列表
     *
     * @param packages 需要扫描的包路径
     */
    private void initHandlerMapping(String packages) {

    }

    private void initHandlerAdapter() {

    }

    private void initHandlerMethod(String packages) {
        Map<String, HandlerMethod> handlerMethodMap = new HashMap<>();
        if (!StringUtils.isNotBlank(packages))
            throw new IllegalArgumentException("there is no packageNames found!");

        String[] packageNames = packages.split(PACKAGE_SEPARATOR);
        if (packageNames == null || packageNames.length < 1)
            throw new IllegalArgumentException("there is no packageNames found!");


        List<Class> classesHasAnnotation = new LinkedList<>();
        for (String pack : packageNames) {
            Class<?>[] classes = ClassScan.scanPackage(pack);
            if (null == classes)
                continue;

            for (Class<?> clazz : classes) {
                if (null != clazz.getAnnotation(Controller.class)) {
                    handlerMethodMap.putAll(scanClassMethod(clazz));
                }
            }
        }
        this.handlerMethodMap = Collections.unmodifiableMap(handlerMethodMap);
    }

    /**
     * 扫描类中包含RequestMapping映射的方法
     *
     * @param clazz
     */
    private Map<String, HandlerMethod> scanClassMethod(Class<?> clazz) {
        Map<String, HandlerMethod> handlerMethodMap = new HashMap<>();
        String prefixMappingUrl = URL_SEPARATOR;
        RequestMapping classMapping = clazz.getAnnotation(RequestMapping.class);
        if (null != classMapping) {
            prefixMappingUrl = URL_SEPARATOR + classMapping.path().replaceFirst(URL_SEPARATOR, "");
        }

        //不支持继承的方法，不支持非public的方法
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            RequestMapping requestMapping;
            if (Modifier.isPublic(method.getModifiers())
                    && null != (requestMapping = method.getAnnotation(RequestMapping.class))) {
                String url = prefixMappingUrl + URL_SEPARATOR + requestMapping.path().replaceFirst(URL_SEPARATOR, "");

                HandlerMethod handlerMethod = new HandlerMethod();
                handlerMethod.setControlerClass(clazz);
                handlerMethod.setMethod(method);
                handlerMethodMap.put(url, handlerMethod);
            }
            //排除非public方法
        }
        return handlerMethodMap;
    }
}
