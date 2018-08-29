package com.github.time69.simple_springmvc.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述: 内嵌式jetty的使用测试
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/21
 * @see //www.eclipse.org/jetty/documentation/9.3.x/embedding-jetty.html
 */
public class EmbedJettyTest {
    private Server server;

    @Before
    public void construct() {
        server = new Server();
    }

    @Test
    public void startJettyServer() {

        final int port = 8080;
        server.addConnector(JettyFactory.createServerConnector(server, port));

        Map<Class<? extends Servlet>,String> servletMap = new HashMap<>();
        servletMap.put(DispatcherServlet.class,"/*");
        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{
                JettyFactory.createResourceHandler("./webapp/"),
                JettyFactory.createWebAppContextHandler("/jetty","./webapp",servletMap),
//                JettyFactory.createServletContextHandler("/jetty","./webapp",servletMap),
                new DefaultHandler()
        });

        server.setHandler(handlerList);
        // JVM退出时关闭Jetty
        server.setStopAtShutdown(true);
        try {
            server.start();
            server.dumpStdErr();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        final int port = 8080;
        Server server = new Server();

        // HTTP connector
        ServerConnector http = new ServerConnector(server);
        http.setHost("localhost");
        http.setPort(port);
        http.setIdleTimeout(30000);
        server.addConnector(http);

        server.setHandler(new HelloHandler());
        server.setErrorHandler(new ErrorHandler());

        server.start();
        server.dumpStdErr();
        server.join();
    }
}
