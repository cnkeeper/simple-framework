package com.github.cnkeep.arthas.server;

import com.github.cnkeep.arthas.Agent;
import com.github.cnkeep.arthas.command.invoker.JstackInvoker;
import com.github.cnkeep.arthas.command.invoker.JvmInvoker;
import com.github.cnkeep.arthas.event.Event;
import com.github.cnkeep.arthas.event.EventListener;
import com.github.cnkeep.arthas.event.EventManager;
import com.github.cnkeep.arthas.transformer.MineClassFileTransformer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 用于处理模块的HTTP请求
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class ModuleHttpServlet extends HttpServlet {
    private static final String SLASH = "/";
    private Map<String, Class> hasTransformedClass = new HashMap<>(64);
    private EventManager eventManager = new EventManager();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(resp);
        printParams(req);
        String command = parseCommand(req);

        String classname = req.getParameter("classname");
        String response = "OK";
        switch (command) {
            case "install":
                install(classname);
                break;
            case "uninstall":
                uninstall(classname);
                break;
            case "jvm":
                response = JvmInvoker.jvmInfo();
                break;
            case "jstack":
                response = JstackInvoker.jstack();
                break;
        }

        resp.getOutputStream().print(response);
    }

    private String parseCommand(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        int indexOf = pathInfo.lastIndexOf("/");
        return pathInfo.substring(indexOf + 1);
    }

    private void printParams(HttpServletRequest req) {
        for (Map.Entry<String, String[]> entry : req.getParameterMap().entrySet()) {
            System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
    }

    private void install(String classname) {
        if (null != classname) {
            try {
                // fixme 这里临时listenId写死，业务可以自行拓展
                String listenId = String.valueOf(System.currentTimeMillis());
                EventListener printEventListener = new EventListener() {

                    @Override
                    public Object onEvent(Event event) {
                        System.out.println("业务自行回调函数处理");
                        if (event.getReturnType() == String.class) {
                            return "modify value";
                        }
                        return event.getReturnValue();
                    }
                };

                EventManager.getInstance().registerEventListener(listenId, printEventListener);

                MineClassFileTransformer transformer = new MineClassFileTransformer(Agent.instrumentation, listenId);
                Agent.instrumentation.addTransformer(transformer, true);

                Class<?> aClass = Class.forName(classname);
                Agent.instrumentation.retransformClasses(aClass);

                Agent.instrumentation.removeTransformer(transformer);
                hasTransformedClass.put(classname, aClass);
            } catch (UnmodifiableClassException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void uninstall(String classname) {
        Class aClass = hasTransformedClass.get(classname);
        if (null != aClass) {
            try {
                Agent.instrumentation.retransformClasses(aClass);
            } catch (UnmodifiableClassException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       throw new UnsupportedOperationException("not supported post");
    }

}
