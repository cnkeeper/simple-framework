package com.github.cnkeep.simple_springmvc.test.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * 描述: 内嵌式tomcat的使用测试
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/21
 */
public class EmbedTomcatTest {

    public static void main(String[] args) {
        Tomcat tomcat = TomcatFactory.createTomcat(8070);
//        String webappDir = new File("webapp").getAbsolutePath();
//        tomcat.addWebapp(contextPath, webappDir);
        String contextPath = "/tomcat";
        String docBase = new File(".").getAbsolutePath();

        Context context = TomcatFactory.createAndAddContext(tomcat, contextPath, docBase, null);

        TomcatFactory.addListener(context,new WebServletContextListener());

        TomcatFactory.addFilter(context, "helloFilter", "/*", new HelloFilter());

        TomcatFactory.addServlet(tomcat, context, "helloServlet", "/*", new HelloServlet());


        // curl http://localhost:8080/tomcat/hello/
        try {
            TomcatFactory.start(tomcat);
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }

}
