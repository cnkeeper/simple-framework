package com.github.time69.simple_springmvc.jetty;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 描述: 内嵌式jetty的使用测试
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/21
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
        server.addConnector(createServerConnector(server, port));

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

    /**
     * @return Connector
     */
    public ServerConnector createServerConnector(Server server, int port) {
        ServerConnector httpServerConnector = new ServerConnector(server);
        httpServerConnector.setHost("localhost");
        httpServerConnector.setPort(port);
        httpServerConnector.setIdleTimeout(30 * 1000);
        return httpServerConnector;
    }

    /**
     * 创建Servlet处理器
     *
     * @return Servlet处理器
     */
    public static ServletHandler createServletHandler() {
        ServletHandler servletHandler = new ServletHandler();
//        servletHandler.addServletWithMapping(createHuluServletHolder(), "/*");
        return servletHandler;
    }


    /**
     * 静态资源处理器
     *
     * @return 静态资源处理器
     */
    public static ResourceHandler createResourceHandler(String resourceBase) {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setResourceBase(resourceBase);
        return resourceHandler;
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

        server.setHandler(new HelloWorldHandler());
        server.setErrorHandler(new ErrorHandler());

        server.start();
        server.dumpStdErr();
        server.join();
    }

    private static class HelloWorldHandler extends AbstractHandler {

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            // Declare response encoding and types
            response.setContentType("text/html; charset=utf-8");

            // Declare response status code
            response.setStatus(HttpServletResponse.SC_OK);

            // Write back response
            response.getWriter().println("<h1>Jetty Hello World!</h1>");

            // Inform jetty that this request has now been handled
            baseRequest.setHandled(true);
        }
    }

    @SuppressWarnings("serial")
    public static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException,
                IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>Hello from HelloServlet</h1>");
        }
    }

}
