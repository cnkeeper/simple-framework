package com.github.time69.simple_springmvc.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * 描述: 内嵌式tomcat的使用测试
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/21
 */
public class EmbedTomcatTest {

    public static void main(String[] args) {
        Tomcat tomcat = TomcatFactory.createTomcat(8080);
//        String webappDir = new File("webapp").getAbsolutePath();
//        tomcat.addWebapp(contextPath, webappDir);
        String contextPath = "/tomcat";
        String docBase = new File(".").getAbsolutePath();

        Context context = TomcatFactory.createAndAddContext(tomcat, contextPath, docBase, null);

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
