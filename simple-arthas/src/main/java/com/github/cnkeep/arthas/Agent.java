package com.github.cnkeep.arthas;


import com.github.cnkeep.arthas.server.JettyCoreServer;
import com.github.cnkeep.arthas.transformer.MineClassFileTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @description: agent启动入口
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/9/17
 * @version: 1.0
 **/
public class Agent {
    public static ClassFileTransformer classFileTransformer;
    public static Instrumentation instrumentation;

    public static void premain(String featureString, Instrumentation inst) {
        System.out.println("premain................");
        System.out.println(featureString);
        System.out.println(inst);
    }

    public static void agentmain(String featureString, Instrumentation inst) {
        System.out.println("agentmain...............");
        System.out.println(featureString);
        System.out.println(inst);
        classFileTransformer = new MineClassFileTransformer(inst);
        instrumentation = inst;
        try {
            JettyCoreServer.getInstance().start(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
