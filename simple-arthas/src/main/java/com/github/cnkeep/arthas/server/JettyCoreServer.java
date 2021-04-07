package com.github.cnkeep.arthas.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.net.InetSocketAddress;


public class JettyCoreServer {

    private static volatile JettyCoreServer coreServer;

    private Server httpServer;


    public void start(int serverPort) throws Exception {
        initHttpServer(serverPort);
        initJettyContextHandler();
        httpServer.start();
    }


    /**
     * 单例
     *
     * @return CoreServer单例
     */
    public static JettyCoreServer getInstance() {
        if (null == coreServer) {
            synchronized (JettyCoreServer.class) {
                if (null == coreServer) {
                    coreServer = new JettyCoreServer();
                }
            }
        }
        return coreServer;
    }


    /*
     * 初始化Jetty's ContextHandler
     */
    private void initJettyContextHandler() {
        final ServletContextHandler context = new ServletContextHandler();

        final String contextPath = "/";
        context.setContextPath(contextPath);
        context.setClassLoader(getClass().getClassLoader());


        // module-http-servlet
        final String pathSpec = "/http/*";
        System.out.println("initializing http-handler. path=" + contextPath + pathSpec);
        context.addServlet(
                new ServletHolder(new ModuleHttpServlet()),
                pathSpec
        );

        httpServer.setHandler(context);
    }

    private void initHttpServer(int serverPort) {
        httpServer = new Server(new InetSocketAddress(serverPort));
    }
}
