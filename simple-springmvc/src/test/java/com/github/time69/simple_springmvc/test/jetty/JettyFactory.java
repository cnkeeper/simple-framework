package com.github.time69.simple_springmvc.test.jetty;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import java.util.*;

/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/22
 */
public final class JettyFactory {

    private JettyFactory() {
        throw new UnsupportedOperationException();
    }

    public static Server createServer(int port) {
        Server server = new Server();
        return server;
    }

    /**
     * 创建http connector
     *
     * @param server
     * @param port
     * @return
     */
    public static ServerConnector createServerConnector(Server server, int port) {
        ServerConnector httpServerConnector = new ServerConnector(server, createHttpConnectionFactory(false));
        httpServerConnector.setHost("localhost");
        httpServerConnector.setPort(port);
        httpServerConnector.setIdleTimeout(30 * 1000);
        return httpServerConnector;
    }

    /**
     * 创建HttpConnectionFactory
     *
     * @return HttpConnectionFactory
     */
    public static HttpConnectionFactory createHttpConnectionFactory(boolean ssl) {
        return new HttpConnectionFactory(createHttpConfiguration(ssl));
    }

    public static HttpConfiguration createHttpConfiguration(boolean ssl) {
        // HTTP Configuration
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme(ssl ? "https" : "http");
        httpConfig.setSecurePort(8443);
        httpConfig.setOutputBufferSize(32768);
        httpConfig.setRequestHeaderSize(8192);
        httpConfig.setResponseHeaderSize(8192);
        httpConfig.setSendServerVersion(false);
        httpConfig.setSendDateHeader(false);
        return httpConfig;
    }

    /**
     * 创建https connector
     * @param server
     * @param port
     * @return
     */
//    public static ServerConnector createSSLServerConnector(Server server, int port) {
//        // SSL Context Factory
//        SslContextFactory sslContextFactory = new SslContextFactory();
//        sslContextFactory.setKeyStorePath(jetty_home + "/../../../jetty-server/src/test/config/etc/keystore");
//        sslContextFactory.setKeyStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
//        sslContextFactory.setKeyManagerPassword("OBF:1u2u1wml1z7s1z7a1wnl1u2g");
//        sslContextFactory.setTrustStorePath(jetty_home + "/../../../jetty-server/src/test/config/etc/keystore");
//        sslContextFactory.setTrustStorePassword("OBF:1vny1zlo1x8e1vnw1vn61x8g1zlu1vn4");
//        sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
//                "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
//                "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
//                "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
//                "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
//                "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");
//
//        // SSL HTTP Configuration
//        HttpConfiguration httpsConfig = new HttpConfiguration(createHttpConfiguration(true));
//        httpsConfig.addCustomizer(new SecureRequestCustomizer());
//
//        // SSL Connector
//        ServerConnector sslConnector = new ServerConnector(server,
//                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
//                new HttpConnectionFactory(httpsConfig));
//        sslConnector.setPort(port);
//        return sslConnector;
//    }

    /**
     * 静态资源处理器
     *
     * @return 静态资源处理器
     */
    public static ResourceHandler createResourceHandler(String resourceBase) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{"index.html", "index.jsp"});
        resourceHandler.setResourceBase(resourceBase);
        return resourceHandler;
    }

    public static ServletHandler createServletHandler(Class<Servlet> servlet, String urlPattern) {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(servlet, urlPattern);
        return handler;
    }

    public static ContextHandler createContextHandler(Handler handler, String contextPath) {
        ContextHandler context = new ContextHandler();
        context.setContextPath(contextPath);
        context.setHandler(handler);
        return context;
    }

    public static ServletContextHandler createServletContextHandler(String contextPath, String resourceBase, Map<Class<? extends Servlet>, String>... servletS) {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextPath);
        context.setResourceBase(resourceBase);

        if (null != servletS && servletS.length == 1 && null != servletS[0]) {
            for (Map.Entry<Class<? extends Servlet>, String> entry : servletS[0].entrySet()) {
                context.addServlet(entry.getKey(), entry.getValue());
            }
        }
        // Add default servlet
//        context.addServlet(DefaultServlet.class, "/");
//        server.setHandler(context);
        return context;
    }

    public static WebAppContext createWebAppContextHandler(String contextPath, String resourceBase, Map<Class<? extends Servlet>, String>... servletS) {
        WebAppContext webAppContext = new WebAppContext();

        webAppContext.setContextPath(contextPath);
        webAppContext.setResourceBase(resourceBase);

        // add servlet
        if (null != servletS && servletS.length == 1 && null != servletS[0]) {
            for (Map.Entry<Class<? extends Servlet>, String> entry : servletS[0].entrySet()) {
                webAppContext.addServlet(entry.getKey(), entry.getValue());
            }
        }

        webAppContext.addEventListener(new WebServletContextListener());
        return webAppContext;
    }

    public static void addServlet(ServletContextHandler context, Servlet servlet, String urlPattern) {
        context.addServlet(servlet.getClass(), urlPattern);
    }

    public static void addFilter(ServletContextHandler context, Filter filter, String urlPattern, DispatcherType... dispatcherTypes) {
        if (null == dispatcherTypes)
            dispatcherTypes = new DispatcherType[]{DispatcherType.REQUEST};

        context.addFilter(filter.getClass(), urlPattern, EnumSet.copyOf(Arrays.asList(dispatcherTypes)));
    }

    public static void addListener(ServletContextHandler context, EventListener listener){
        context.addEventListener(listener);
    }
}
