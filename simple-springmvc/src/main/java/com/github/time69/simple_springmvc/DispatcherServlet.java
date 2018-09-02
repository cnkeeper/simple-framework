package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.context.ApplicationContext;
import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;
import com.github.time69.simple_springmvc.handler.mapping.RequestMappingHandlerMapping;
import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.LoggerContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 描述: 前置控制器，所有被拦截的请求入口
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerContext.getLog(DispatcherServlet.class);
    private static final String KEY_PACKAGENAMES = "packageNames";
    private List<HandlerMapping> handlerMappings;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolverss;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //首次加载时扫描Controller，RequestMapping注解
        ApplicationContext.initHandlerMethod(config.getInitParameter(KEY_PACKAGENAMES));
        initHandlerMapping();
        initHandlerAdapter();
        initViewResolvers();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatcher(req, resp);
        } catch (Exception e) {
            // TODO 异常处理
        }

//        super.service(req, resp);
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HandlerExecutionChain executionChain = getHandlerExecutionChain(req);
        HandlerAdapter handlerAdapter = getHandlerAdapter(executionChain.getHandler());

        //前置拦截
        if (!executionChain.applyPreHandler(req, resp)) {
            //拦截器拦截，不继续处理
            return;
        }

        ModelAndView modelAndView = null;
        Exception ex = null;

        try {
            //请求处理
            modelAndView = handlerAdapter.handler(req, resp, executionChain.getHandler());

            //后置拦截
            executionChain.applyPostHandler(req, resp, modelAndView);
        } catch (Exception e) {
            ex = e;
        }
        // TODO 解析处理结果
        processDispatchResult(req, resp, modelAndView,ex);
    }

    private HandlerExecutionChain getHandlerExecutionChain(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            HandlerExecutionChain handlerChain = handlerMapping.getHander(req);
            if (null != handlerChain) {
                return handlerChain;
            }
        }
        return null;
    }

    private HandlerAdapter getHandlerAdapter(Handler handler) {
        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        return null;
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView, Exception ex) throws Exception {
        View view = null;

        if(null != ex){
            // TODO 发成异常，异常处理器
        }

        if (modelAndView.isReference()) {
            //需要映射到真实的视图上
            view = resolveViewName(modelAndView.getViewName());
            if (null == view)
                throw new ServletException(String.format("ModelAndView[%s] not contains viewName[%s]", modelAndView.toString(), modelAndView.getViewName()));
        } else {
            //不需要映射，本身已经包含视图
            view = modelAndView.getView();
            if (null == view)
                throw new ServletException(String.format("ModelAndView[%s] neither contains View nor contains viewName[%s]", modelAndView.toString(), modelAndView.getViewName()));
        }

        view.render(modelAndView.getModelMap(), req, resp);
    }

    /**
     * 视图解析获取视图
     *
     * @param viewName
     * @return
     */
    private View resolveViewName(String viewName) {
        View view = null;
        for (ViewResolver viewResolver : this.viewResolverss) {
            view = viewResolver.resolveView(viewName);
            if (null != view) {
                break;
            }
        }
        return view;
    }

    /**
     * TODO 初始化加载handlerMappings
     */
    private void initHandlerMapping() {
        this.handlerMappings = Collections.emptyList();
        this.handlerMappings.add(new RequestMappingHandlerMapping());
    }

    /**
     * 初始化加载handlerAdapters
     */
    private void initHandlerAdapter() {
        this.handlerAdapters = Collections.emptyList();
    }

    private void initViewResolvers() {
        this.viewResolverss = Collections.emptyList();
    }
}
