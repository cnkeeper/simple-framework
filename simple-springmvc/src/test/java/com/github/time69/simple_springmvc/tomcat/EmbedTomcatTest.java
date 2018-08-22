package com.github.time69.simple_springmvc.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述: 内嵌式tomcat的使用测试
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/21
 */
public class EmbedTomcatTest {
    private Tomcat tomcat;

    @Before
    public void construct() {
        tomcat = new Tomcat();
    }


    @Test
    public void testEmbedTomcat() throws ServletException, LifecycleException {
        tomcat.setBaseDir("tmp");
        tomcat.setHostname("localhost");
        tomcat.setPort(8080);

        String contextPath = "/tomcat";
//        String webappDir = new File("webapp").getAbsolutePath();
//        tomcat.addWebapp(contextPath, webappDir);

        String docBase = new File(".").getAbsolutePath();
        Context context = addContxt(contextPath, docBase);
        addServlet(context);

        // curl http://localhost:8080/tomcat/hello/
        start();
    }


    /**
     * 启动服务
     */
    public void start() throws LifecycleException {
        tomcat.start();
        tomcat.getServer().await();
    }


    /**
     * contextPath配置 <br/>
     * 等效于配置conf/server.xml
     * <pre>
     *     <Context path="/" docBase="/ump-wwwroot" reloadable="true"></Context>
     * </pre>
     */
    private Context addContxt(String contextPath, String docBase) {
//        String contextPath = "/";
//        String docBase = new File(".").getAbsolutePath();
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
        context.addParameter("key", "value");
        return context;
    }

    /**
     * servlet配置  <br/>
     * 等效于在web.xml配置servlet, servlet-mapping节点
     *
     * @param context
     */
    private void addServlet(Context context) throws ServletException {
        String servletName = "helloServlet";
        String urlPattern = "/hello";
        HttpServlet servlet = new HelloServlet();
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
    private void addFilter(Context context) {
        // filter
        FilterDef filterDef = new FilterDef();
        filterDef.addInitParameter("key", "value");

        filterDef.setFilter(new HelloFilter());
        filterDef.setFilterName("helloFilter");
        context.addFilterDef(filterDef);

        //filter-mapping
        FilterMap filterMap = new FilterMap();
        filterMap.addURLPattern("/*");
        filterMap.setFilterName("helloFilter");
        context.addFilterMap(filterMap);
    }
}
