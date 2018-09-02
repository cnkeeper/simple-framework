package com.github.time69.simple_springmvc.context;

import com.github.time69.simple_springmvc.annotation.Controller;
import com.github.time69.simple_springmvc.annotation.RequestMapping;
import com.github.time69.simple_springmvc.handler.HandlerMethod;
import com.github.time69.simple_springmvc.util.ClassScan;
import com.github.time69.simple_springmvc.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 描述： 全局容器，类似于Spring的ApplicationContext
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public final class ApplicationContext {

    private static final String PACKAGE_SEPARATOR = ",";

    private static final String URL_SEPARATOR = "/";

    public static Map<String, HandlerMethod> BEAN_HANDLER_METHOD_MAP;

    private static Map<Class,List> container = Collections.emptyMap();

    public static <T> List<T> getBean(Class<T> clazz){
        return container.get(clazz);
    }

    public static void initHandlerMethod(String packages) {
        Map<String, HandlerMethod> handlerMethodMap = new HashMap<>();
        if (!StringUtils.isNotBlank(packages))
            throw new IllegalArgumentException("there is no packageNames found!");

        String[] packageNames = packages.split(PACKAGE_SEPARATOR);
        if (packageNames == null || packageNames.length < 1)
            throw new IllegalArgumentException("there is no packageNames found!");


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
        BEAN_HANDLER_METHOD_MAP = Collections.unmodifiableMap(handlerMethodMap);
    }


    /**
     * 扫描类中包含RequestMapping映射的方法
     *
     * @param clazz
     */
    private static Map<String, HandlerMethod> scanClassMethod(Class<?> clazz) {
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
                handlerMethod.setClassType(clazz);
                handlerMethod.setMethod(method);
                handlerMethodMap.put(url, handlerMethod);
            }
            //排除非public方法
        }
        return handlerMethodMap;
    }


}
