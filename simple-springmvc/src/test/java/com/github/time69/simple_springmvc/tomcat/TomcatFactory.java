package com.github.time69.simple_springmvc.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/22
 */
public final class TomcatFactory {
    private TomcatFactory() {
        throw new UnsupportedOperationException();
    }

    public static Tomcat createTomcat(final int port) {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("tmp");
        tomcat.setPort(port);
        return tomcat;
    }

    /**
     * contextPath配置 <br/>
     * 等效于配置conf/server.xml
     * <pre>
     *     <Context path="/" docBase="/ump-wwwroot" reloadable="true"></Context>
     * </pre>
     *
     * @param tomcat
     * @param contextPath
     * @param docBase
     * @param contextParams
     * @return
     */
    public static Context createAndAddContext(Tomcat tomcat, String contextPath, String docBase, Map<String, String>... contextParams) {
        Context context = tomcat.addContext(contextPath, docBase);

        context.addErrorPage(new ErrorPage());
        context.setCookies(true);
        context.setSessionTimeout(30);

        /**
         * <pre>
         * 等效于配置:
         * <context-param>
         *     <param-name>key</param-name>
         *     <param-value>value</param-value>
         * </context-param>
         * </pre>
         */
        if (null != contextParams && contextParams.length == 1 && null != contextParams[0]) {
            for (Map.Entry<String, String> entry : contextParams[0].entrySet()) {
                context.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return context;
    }


    /**
     * servlet配置  <br/>
     * 等效于在web.xml配置servlet, servlet-mapping节点
     *
     * @param context
     */
    public static void addServlet(Tomcat tomcat, Context context, String servletName, String urlPattern, Servlet servlet) {
        tomcat.addServlet(context, servletName, servlet);
        context.addServletMappingDecoded(urlPattern, servletName);
    }

    /**
     * <pre>
     *     &lt;filter&gt;
     *         &lt;filter-name&gt;helloFilter&lt;/filter-name&gt;
     *         &lt;filter-class&gt;***.HelloFilter&lt;/filter-class&gt;
     *         &lt;inti-param&gt;
     *             &lt;param—name&gt;key&lt;/param—name&gt;
     *             &lt;param-value&gt;value&lt;/param-value&gt;
     *         &lt;/inti-param&gt;
     *     &lt;/filter&gt;
     *     &lt;filter-mapping&gt;
     *          &lt;filter-name&gt;helloFilter&lt;/filter-name&gt;
     *          &lt;url-pattern&gt;/*&lt;/url-pattern>
     *      &lt;/filter-mapping&gt;
     * </pre>
     */
    public static void addFilter(Context context, String filterName, String urlPattern, Filter filter, Map<String, String>... initParam) {
        // filter
        FilterDef filterDef = new FilterDef();
        if (initParam != null && initParam.length == 1 && null != initParam[0]) {
            for (Map.Entry<String, String> entry : initParam[0].entrySet()) {
                filterDef.addInitParameter(entry.getKey(), entry.getValue());
            }
        }

        filterDef.setFilter(filter);
        filterDef.setFilterName(filterName);
        context.addFilterDef(filterDef);

        //filter-mapping
        FilterMap filterMap = new FilterMap();
        filterMap.addURLPattern(urlPattern);
        filterMap.setFilterName(filterName);
        context.addFilterMap(filterMap);
    }

    /**
     * 启动tomcat服务
     * @param tomcat
     * @throws LifecycleException
     */
    public static void start(Tomcat tomcat) throws LifecycleException {
        tomcat.start();
        tomcat.getServer().await();
    }

    public static void stop(Tomcat tomcat) throws LifecycleException {
        tomcat.stop();
        tomcat.getServer().stop();
    }
}
