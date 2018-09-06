package com.github.time69.simple_springmvc;

import com.github.time69.simple_springmvc.context.ApplicationContext;
import com.github.time69.simple_springmvc.handler.HandlerExecutionChain;
import com.github.time69.simple_springmvc.logger.Logger;
import com.github.time69.simple_springmvc.logger.LoggerContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
    }

    /**
     * <ul>
     * <li>1. 拦截请求</li>
     * <li>2. 查找处理器</li>
     * <li>3. 处理器处理，获取结果</li>
     * <li>4. 解析视图，获取视图</li>
     * <li>5. 绑定数据模型</li>
     * </ul>
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //查找适配处理器
        HandlerExecutionChain executionChain = getHandlerExecutionChain(req);

        //查找适配的处理器执行器
        HandlerAdapter handlerAdapter = getHandlerAdapter(executionChain.getHandler());

        //前置拦截
        if (!executionChain.applyPreHandler(req, resp)) {
            //拦截器已经拦截处理，不继续执行
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
            LOGGER.error("doDispatcher has error, occur by{}", e);
            ex = e;
        }
        //解析处理结果
        processDispatchResult(req, resp, modelAndView, ex);
    }

    private HandlerExecutionChain getHandlerExecutionChain(HttpServletRequest req) {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            HandlerExecutionChain handlerChain = handlerMapping.getHandler(req);
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

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView modelAndView, Exception dispatchException) throws Exception {
        //有可能是@ResponseBody，在执行器处理时就已经返回，此时view为空，不需要视图解析
        if (null == modelAndView)
            return;

        View view = null;
        if (null != dispatchException) {
            // TODO 发成异常，异常处理器
        }

        if (modelAndView.isReference()) {
            //视图解析，需要映射到真实的视图上, 此时的View实质上是String
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
     * 初始化加载handlerMappings
     */
    private void initHandlerMapping() {
        this.handlerMappings = new ArrayList<>(0);
        List<HandlerMapping> handlerMappingList = ApplicationContext.getBean(HandlerMapping.class);
        if (null != handlerMappingList)
            this.handlerMappings.addAll(ApplicationContext.getBean(HandlerMapping.class));
    }

    /**
     * 初始化加载handlerAdapters
     */
    private void initHandlerAdapter() {
        this.handlerAdapters = new ArrayList<>(0);
        List<HandlerAdapter> handlerAdapterList = ApplicationContext.getBean(HandlerAdapter.class);
        if (handlerAdapterList != null) {
            this.handlerAdapters.addAll(handlerAdapterList);
        }
    }

    private void initViewResolvers() {
        this.viewResolverss = new ArrayList<>(0);
        List<ViewResolver> viewResolverList = ApplicationContext.getBean(ViewResolver.class);
        if (viewResolverList != null) {
            this.viewResolverss.addAll(viewResolverList);
        }
    }
}
