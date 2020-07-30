package com.github.cnkeep.simple_springmvc.context;

import com.github.cnkeep.simple_springmvc.HandlerAdapter;
import com.github.cnkeep.simple_springmvc.HandlerMapping;
import com.github.cnkeep.simple_springmvc.handler.MethodParameter;
import com.github.cnkeep.simple_springmvc.util.ClassScan;
import com.github.cnkeep.simple_springmvc.util.StringUtils;
import com.github.cnkeep.simple_springmvc.annotation.Controller;
import com.github.cnkeep.simple_springmvc.annotation.RequestMapping;
import com.github.cnkeep.simple_springmvc.handler.MethodHandler;
import com.github.cnkeep.simple_springmvc.handler.support.adapter.RequestMappingHandlerAdapter;
import com.github.cnkeep.simple_springmvc.handler.support.adapter.UrlResourceHandlerAdapter;
import com.github.cnkeep.simple_springmvc.handler.support.mapping.RequestMappingHandlerMapping;
import com.github.cnkeep.simple_springmvc.handler.support.mapping.UrlResourceHandlerMapping;
import com.github.cnkeep.simple_springmvc.resolver.arguments.ContextMethodHandlerArgsResolver;
import com.github.cnkeep.simple_springmvc.resolver.arguments.EntityMethodHandlerArgsResolver;
import com.github.cnkeep.simple_springmvc.resolver.arguments.MapMethodHandlerArgsResolver;
import com.github.cnkeep.simple_springmvc.resolver.arguments.MethodHandlerArgResolver;
import com.github.cnkeep.simple_springmvc.resolver.view.UrlResourceViewResolver;
import com.github.cnkeep.simple_springmvc.resolver.view.ViewResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 描述： 全局容器，类似于Spring的ApplicationContext
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public final class ApplicationContext {

    private static final String PACKAGE_SEPARATOR = ",";

    private static final String URL_SEPARATOR = "/";

    public static Map<String, MethodHandler> BEAN_HANDLER_METHOD_MAP;

    private static Map<Class, List> container = new HashMap<>();

    public static <T> List<T> getBean(Class<T> clazz) {
        return container.get(clazz);
    }

    public static void initHandlerMethod(String packages) {
        Map<String, MethodHandler> handlerMethodMap = new HashMap<>(0);
        if (!StringUtils.isNotBlank(packages)) {
            throw new IllegalArgumentException("there is no packageNames found!");
        }

        String[] packageNames = packages.split(PACKAGE_SEPARATOR);
        if (packageNames == null || packageNames.length < 1) {
            throw new IllegalArgumentException("there is no packageNames found!");
        }


        for (String pack : packageNames) {
            Class<?>[] classes = ClassScan.scanPackage(pack);
            if (null == classes) {
                continue;
            }

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
    private static Map<String, MethodHandler> scanClassMethod(Class<?> clazz) {
        Map<String, MethodHandler> handlerMethodMap = new HashMap<>(0);
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

                MethodHandler methodHandler = new MethodHandler();
                methodHandler.setClassType(clazz);
                methodHandler.setMethod(method);
                Parameter[] parameters = method.getParameters();
                MethodParameter[] methodParameters = new MethodParameter[parameters.length];
                for (int index = 0; index < parameters.length; index++) {
                    methodParameters[index] = new MethodParameter(parameters[index].getType(),parameters[index],index,null);
                }
                methodHandler.setMethodParameters(methodParameters);

                handlerMethodMap.put(url, methodHandler);
            }
            //排除非public方法
        }
        return handlerMethodMap;
    }

    public static void refresh() {

        /**
         * MethodHandlerArgResolver注入容器
         */
        List<MethodHandlerArgResolver> methodHandlerArgResolverList = new ArrayList<>(8);
        methodHandlerArgResolverList.add(new MapMethodHandlerArgsResolver());
        methodHandlerArgResolverList.add(new ContextMethodHandlerArgsResolver());
        methodHandlerArgResolverList.add(new EntityMethodHandlerArgsResolver());
        container.put(MethodHandlerArgResolver.class, Collections.unmodifiableList(methodHandlerArgResolverList));

        /**
         * ViewResolver注入容器
         */
        List<ViewResolver> viewResolverList = new ArrayList<>(1);
        viewResolverList.add(new UrlResourceViewResolver());
        container.put(ViewResolver.class, Collections.unmodifiableList(viewResolverList));

        /**
         * HandlerMapping注入容器
         */
        List<HandlerMapping> handlerMappingList = new ArrayList();
        handlerMappingList.add(new RequestMappingHandlerMapping());
        handlerMappingList.add(new UrlResourceHandlerMapping());
        container.put(HandlerMapping.class, Collections.unmodifiableList(handlerMappingList));

        /**
         * HandlerAdapter注入容器
         */
        List<HandlerAdapter> handlerAdapterList = new ArrayList<>();
        handlerAdapterList.add(new RequestMappingHandlerAdapter());
        handlerAdapterList.add(new UrlResourceHandlerAdapter());
        container.put(HandlerAdapter.class, Collections.unmodifiableList(handlerAdapterList));


        container = Collections.unmodifiableMap(container);
    }
}
