package com.github.cnkeep.simple_springmvc.test.jetty;

import com.github.cnkeep.simple_springmvc.DispatcherServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述: 内嵌式jetty的使用测试
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
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

        ServletHolder servletHolder = new ServletHolder();
        servletHolder.setServlet(new DispatcherServlet());
        servletHolder.setInitOrder(0);
        servletHolder.setInitParameter("packageNames","com.github.cnkeep");
        HandlerList handlerList = new HandlerList();

        Map<String,ServletHolder> servletHolderMap = new LinkedHashMap<>(0);
        servletHolderMap.put("/*",servletHolder);

        handlerList.setHandlers(new Handler[]{
                JettyFactory.createResourceHandler("./webapp/"),
                JettyFactory.createWebAppContextHandler("/jetty","./webapp",servletHolderMap),
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
