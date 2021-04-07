package com.github.cnkeep.arthas;

import com.sun.tools.attach.*;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.concurrent.CountDownLatch;

/**
 * @description: agent入口
 * <pre>
 *      ➜  ~ curl "127.0.0.1:8080/http/uninstall?classname=test.jdk.agent.Printer"
 *      ➜  ~ curl "127.0.0.1:8080/http/install?classname=test.jdk.agent.Printer"
 *      ➜  ~ curl "127.0.0.1:8080/http/jvm"
 *      ➜  ~ curl "127.0.0.1:8080/http/thread"
 *      mvn clean package install -Dmaven.test.skip=true
 * </pre>
 * @date: 2020/9/17
 * @version:
 **/
public class AttachBootstrap {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {
        String pid = args[0];
        for (VirtualMachineDescriptor virtualMachineDescriptor : VirtualMachine.list()) {
            System.out.println(virtualMachineDescriptor.displayName());
        }


        VirtualMachine attach = VirtualMachine.attach(pid);
        String agentJarPath = new File("simple-arthas/lib/simple-arthas-agent-1.0-SNAPSHOT.jar").getAbsolutePath();
        attach.loadAgent(agentJarPath);
        for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
            System.out.println(memoryPoolMXBean);
        }

        new CountDownLatch(1).await();
    }

}
