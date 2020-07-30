package com.github.cnkeep.simple_springmvc.handler.support.adapter;

import com.github.cnkeep.simple_springmvc.Handler;
import com.github.cnkeep.simple_springmvc.HandlerAdapter;
import com.github.cnkeep.simple_springmvc.ModelAndView;
import com.github.cnkeep.simple_springmvc.context.ApplicationContext;
import com.github.cnkeep.simple_springmvc.handler.MethodHandler;
import com.github.cnkeep.simple_springmvc.handler.MethodParameter;
import com.github.cnkeep.simple_springmvc.http.MediaType;
import com.github.cnkeep.simple_springmvc.logger.Logger;
import com.github.cnkeep.simple_springmvc.logger.LoggerContext;
import com.github.cnkeep.simple_springmvc.util.JsonUtil;
import com.github.cnkeep.simple_springmvc.annotation.ResponseBody;
import com.github.cnkeep.simple_springmvc.resolver.arguments.MethodHandlerArgResolver;
import com.github.cnkeep.simple_springmvc.resolver.returnValue.MethodHandlerReturnValueResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 描述: 执行器，用于需要controller方法处理的情况
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/30
 */
public class RequestMappingHandlerAdapter implements HandlerAdapter {
    public static final Logger LOGGER = LoggerContext.getLog(RequestMappingHandlerAdapter.class);
    /**
     * 参数解析器列表
     */
    private List<MethodHandlerArgResolver> argsResolvers = new LinkedList<>();

    /**
     * 返回值解析器列表
     */
    private Map<String, MethodHandlerReturnValueResolver> returnValueResolvers = new LinkedHashMap<>(0);

    @Override
    public boolean supports(Handler handler) {
        return (handler instanceof MethodHandler);
    }

    @Override
    public ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
        return invokeHandler(request, response, (MethodHandler) handler);
    }

    private ModelAndView invokeHandler(HttpServletRequest request, HttpServletResponse response, MethodHandler handler) throws Exception {
        ModelAndView modelAndView = null;
        if (handler instanceof MethodHandler) {
            //请求参数封装
            Object[] args = getMethodArgumentValues(request, response, handler);

            //执行处理逻辑
            Object returnValue = invokeMethod(request, handler, args);

            //返回值处理
            modelAndView = resolverReturnValue(response, handler, returnValue);
        }
        return modelAndView;
    }

    /**
     * 执行请求处理的业务逻辑，即controller方法
     *
     * @param request
     * @param handler
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object invokeMethod(HttpServletRequest request, MethodHandler handler, Object[] args) throws InvocationTargetException, IllegalAccessException {
        Method method = handler.getMethod();
        Object beanInstance = handler.getBeanInstance();
        Object returnValue = method.invoke(beanInstance, args);
        return returnValue;
    }

    /**
     * TODO 从请求中获取controller方法的参数，并自动封装为匹配的参数对象列表
     *
     * @param request
     * @param response
     * @param handler  @return
     */
    private Object[] getMethodArgumentValues(HttpServletRequest request, HttpServletResponse response, MethodHandler handler) {
        // resolverMethodArguments
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (this.argsResolvers == null || this.argsResolvers.size() == 0) {
            this.argsResolvers = ApplicationContext.getBean(MethodHandlerArgResolver.class);
        }

        Object[] args = new Object[handler.getMethodParameters().length];
        for (MethodParameter parameter : handler.getMethodParameters()) {
            for (MethodHandlerArgResolver argResolver : this.argsResolvers) {
                if (argResolver.support(parameter)) {
                    args[parameter.getIndex()] = argResolver.handleArgument(request, response, parameter);
                    break;
                }
            }
        }
        LOGGER.info("parameterMap:{}", parameterMap);
        return args;
    }

    /**
     * controller方法返回值处理，视图映射 or 直接返回(ResponseBody)
     *
     * @param response
     * @param methodHandler
     * @param returnValue
     * @return
     */
    private ModelAndView resolverReturnValue(HttpServletResponse response, MethodHandler methodHandler, Object returnValue) throws IOException {
        ModelAndView modelAndView = null;
        if (null == returnValue) {
            return modelAndView;
        }

        if (null != methodHandler.getAnnotation(ResponseBody.class)) {
            /**
             * ResponseBody直接返回，不进行视图解析
             */
            convertReturnValue2Body(response, methodHandler, returnValue);
        } else {
            if (returnValue instanceof ModelAndView) {
                modelAndView = (ModelAndView) returnValue;
            } else if (returnValue instanceof String) {
                modelAndView = new ModelAndView();
                modelAndView.setViewName((String) returnValue);
            }
        }

        return modelAndView;
    }

    /**
     * 返回值转换为执行格式数据直接返回，不做视图解析
     *
     * @param response
     * @param methodHandler
     * @param returnValue
     * @throws IOException
     */
    private void convertReturnValue2Body(HttpServletResponse response, MethodHandler methodHandler, Object returnValue) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.getContentType());
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(null != returnValue ? JsonUtil.serialize(returnValue) : JsonUtil.serialize(""));
        responseWriter.flush();
    }
}
