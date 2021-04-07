package com.github.cnkeep.arthas.command.invoker;

import com.github.cnkeep.arthas.command.Command;
import com.github.cnkeep.arthas.command.CommandContext;
import com.github.cnkeep.arthas.command.CommandInvoker;
import com.github.cnkeep.arthas.command.CommandResult;

import java.lang.management.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @description: jvm信息
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class JvmInvoker implements CommandInvoker {

    private static final long MB = 1024 * 1024;

    public static String jvmInfo() {
        StringBuilder joiner = new StringBuilder();
        //打印系统信息
        joiner.append("===========打印系统信息==========").append(System.lineSeparator());
        joiner.append(operatingSystemInfo()).append(System.lineSeparator());

        //打印编译信息
        joiner.append("===========打印编译信息==========").append(System.lineSeparator());
        joiner.append(compilationInfo()).append(System.lineSeparator());
        //打印类加载信息
        joiner.append("===========打印类加载信息==========").append(System.lineSeparator());
        joiner.append(classLoadingInfo()).append(System.lineSeparator());
        //打印运行时信息
        joiner.append("===========打印运行时信息==========").append(System.lineSeparator());
        joiner.append(runtimeInfo()).append(System.lineSeparator());
        //打印内存管理器信息
        joiner.append("===========打印内存管理器信息==========").append(System.lineSeparator());
        joiner.append(memoryManagerInfo()).append(System.lineSeparator());
        //打印垃圾回收信息
        joiner.append("===========打印垃圾回收信息==========").append(System.lineSeparator());
        joiner.append(garbageCollectorInfo()).append(System.lineSeparator());
        //打印vm内存
        joiner.append("===========打印vm内存信息==========").append(System.lineSeparator());
        joiner.append(memoryInfo()).append(System.lineSeparator());
        //打印vm各内存区信息
        joiner.append("===========打印vm各内存区信息==========").append(System.lineSeparator());
        joiner.append(memoryPoolInfo()).append(System.lineSeparator());
        //打印线程信息
        joiner.append("===========打印线程==========").append(System.lineSeparator());
        joiner.append(threadInfo()).append(System.lineSeparator());

        return joiner.toString();
    }


    public static String operatingSystemInfo() {
        StringBuilder joiner = new StringBuilder();
        OperatingSystemMXBean system = ManagementFactory.getOperatingSystemMXBean();
        //相当于System.getProperty("os.name").
        joiner.append("系统名称:" + system.getName()).append(System.lineSeparator());
        //相当于System.getProperty("os.version").
        joiner.append("系统版本:" + system.getVersion()).append(System.lineSeparator());
        //相当于System.getProperty("os.arch").
        joiner.append("操作系统的架构:" + system.getArch()).append(System.lineSeparator());
        //相当于 Runtime.availableProcessors()
        joiner.append("可用的内核数:" + system.getAvailableProcessors()).append(System.lineSeparator());

        if (isSunOsMBean(system)) {
            long totalPhysicalMemory = getLongFromOperatingSystem(system, "getTotalPhysicalMemorySize");
            long freePhysicalMemory = getLongFromOperatingSystem(system, "getFreePhysicalMemorySize");
            long usedPhysicalMemorySize = totalPhysicalMemory - freePhysicalMemory;

            joiner.append("总物理内存(M):" + totalPhysicalMemory / MB).append(System.lineSeparator());
            joiner.append("已用物理内存(M):" + usedPhysicalMemorySize / MB).append(System.lineSeparator());
            joiner.append("剩余物理内存(M):" + freePhysicalMemory / MB).append(System.lineSeparator());

            long totalSwapSpaceSize = getLongFromOperatingSystem(system, "getTotalSwapSpaceSize");
            long freeSwapSpaceSize = getLongFromOperatingSystem(system, "getFreeSwapSpaceSize");
            long usedSwapSpaceSize = totalSwapSpaceSize - freeSwapSpaceSize;

            joiner.append("总交换空间(M):" + totalSwapSpaceSize / MB).append(System.lineSeparator());
            joiner.append("已用交换空间(M):" + usedSwapSpaceSize / MB).append(System.lineSeparator());
            joiner.append("剩余交换空间(M):" + freeSwapSpaceSize / MB).append(System.lineSeparator());
        }

        return joiner.toString();
    }

    private static long getLongFromOperatingSystem(OperatingSystemMXBean operatingSystem, String methodName) {
        try {
            final Method method = operatingSystem.getClass().getMethod(methodName,
                    (Class<?>[]) null);
            method.setAccessible(true);
            return (Long) method.invoke(operatingSystem, (Object[]) null);
        } catch (final InvocationTargetException e) {
            if (e.getCause() instanceof Error) {
                throw (Error) e.getCause();
            } else if (e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new IllegalStateException(e.getCause());
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        } catch (final IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String compilationInfo() {
        StringBuilder joiner = new StringBuilder();
        CompilationMXBean compilation = ManagementFactory.getCompilationMXBean();
        joiner.append("JIT编译器名称：" + compilation.getName()).append(System.lineSeparator());
        //判断jvm是否支持编译时间的监控
        if (compilation.isCompilationTimeMonitoringSupported()) {
            joiner.append("总编译时间：" + compilation.getTotalCompilationTime() + "秒").append(System.lineSeparator());
        }
        return joiner.toString();
    }

    public static String classLoadingInfo() {
        StringBuilder joiner = new StringBuilder();
        ClassLoadingMXBean classLoad = ManagementFactory.getClassLoadingMXBean();
        joiner.append("已加载类总数：" + classLoad.getTotalLoadedClassCount()).append(System.lineSeparator());
        joiner.append("已加载当前类：" + classLoad.getLoadedClassCount()).append(System.lineSeparator());
        joiner.append("已卸载类总数：" + classLoad.getUnloadedClassCount()).append(System.lineSeparator());

        return joiner.toString();
    }

    public static String runtimeInfo() {
        StringBuilder joiner = new StringBuilder();
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        joiner.append("进程PID=" + runtime.getName().split("@")[0]).append(System.lineSeparator());
        joiner.append("jvm规范名称:" + runtime.getSpecName()).append(System.lineSeparator());
        joiner.append("jvm规范运营商:" + runtime.getSpecVendor()).append(System.lineSeparator());
        joiner.append("jvm规范版本:" + runtime.getSpecVersion()).append(System.lineSeparator());
        //返回虚拟机在毫秒内的开始时间。该方法返回了虚拟机启动时的近似时间
        joiner.append("jvm启动时间（毫秒）:" + runtime.getStartTime()).append(System.lineSeparator());
        //相当于System.getProperties
        joiner.append("获取System.properties:" + runtime.getSystemProperties()).append(System.lineSeparator());
        joiner.append("jvm正常运行时间（毫秒）:" + runtime.getUptime()).append(System.lineSeparator());
        //相当于System.getProperty("java.vm.name").
        joiner.append("jvm名称:" + runtime.getVmName()).append(System.lineSeparator());
        //相当于System.getProperty("java.vm.vendor").
        joiner.append("jvm运营商:" + runtime.getVmVendor()).append(System.lineSeparator());
        //相当于System.getProperty("java.vm.version").
        joiner.append("jvm实现版本:" + runtime.getVmVersion()).append(System.lineSeparator());
        List<String> args = runtime.getInputArguments();
        if (args != null && !args.isEmpty()) {
            joiner.append("vm参数:");
            for (String arg : args) {
                joiner.append(arg);
            }
        }
        joiner.append("类路径:" + runtime.getClassPath()).append(System.lineSeparator());
        joiner.append("引导类路径:" + runtime.getBootClassPath()).append(System.lineSeparator());
        joiner.append("库路径:" + runtime.getLibraryPath()).append(System.lineSeparator());
        return joiner.toString();
    }

    public static String memoryManagerInfo() {
        StringBuilder joiner = new StringBuilder();
        List<MemoryManagerMXBean> managers = ManagementFactory.getMemoryManagerMXBeans();
        if (managers != null && !managers.isEmpty()) {
            for (MemoryManagerMXBean manager : managers) {
                joiner.append("vm内存管理器：名称=" + manager.getName() + ",管理的内存区="
                        + Arrays.deepToString(manager.getMemoryPoolNames()) + ",ObjectName=" + manager.getObjectName())
                        .append(System.lineSeparator());
            }
        }
        return joiner.toString();
    }

    public static String garbageCollectorInfo() {
        StringBuilder joiner = new StringBuilder();
        List<GarbageCollectorMXBean> garbages = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbage : garbages) {
            joiner.append("垃圾收集器：名称=" + garbage.getName() + ",收集=" + garbage.getCollectionCount() + ",总花费时间="
                    + garbage.getCollectionTime() + ",内存区名称=" + Arrays.deepToString(garbage.getMemoryPoolNames()))
                    .append(System.lineSeparator());
        }
        return joiner.toString();
    }

    public static String memoryInfo() {
        StringBuilder joiner = new StringBuilder();
        MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
        MemoryUsage headMemory = memory.getHeapMemoryUsage();
        joiner.append("head堆:").append(System.lineSeparator());
        joiner.append("\t初始(M):" + headMemory.getInit() / MB).append(System.lineSeparator());
        joiner.append("\t最大(上限)(M):" + headMemory.getMax() / MB).append(System.lineSeparator());
        joiner.append("\t当前(已使用)(M):" + headMemory.getUsed() / MB).append(System.lineSeparator());
        joiner.append("\t提交的内存(已申请)(M):" + headMemory.getCommitted() / MB).append(System.lineSeparator());
        joiner.append("\t使用率:" + headMemory.getUsed() * 100 / headMemory.getCommitted() + "%").append(System.lineSeparator());

        joiner.append("non-head非堆:").append(System.lineSeparator());
        MemoryUsage nonheadMemory = memory.getNonHeapMemoryUsage();
        joiner.append("\t初始(M):" + nonheadMemory.getInit() / MB).append(System.lineSeparator());
        joiner.append("\t最大(上限)(M):" + nonheadMemory.getMax() / MB).append(System.lineSeparator());
        joiner.append("\t当前(已使用)(M):" + nonheadMemory.getUsed() / MB).append(System.lineSeparator());
        joiner.append("\t提交的内存(已申请)(M):" + nonheadMemory.getCommitted() / MB).append(System.lineSeparator());
        joiner.append("\t使用率:" + nonheadMemory.getUsed() * 100 / nonheadMemory.getCommitted() + "%").append(System.lineSeparator());
        return joiner.toString();
    }

    public static String memoryPoolInfo() {
        StringBuilder joiner = new StringBuilder();
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        if (pools != null && !pools.isEmpty()) {
            for (MemoryPoolMXBean pool : pools) {
                //只打印一些各个内存区都有的属性，一些区的特殊属性，可看文档或百度
                //最大值，初始值，如果没有定义的话，返回-1，所以真正使用时，要注意
                joiner.append("vm内存区:\n\t名称=" + pool.getName() + "\n\t所属内存管理者=" + Arrays.deepToString(pool.getMemoryManagerNames())
                        + "\n\t ObjectName=" + pool.getObjectName() + "\n\t初始大小(M)=" + pool.getUsage().getInit() / MB
                        + "\n\t最大(上限)(M)=" + pool.getUsage().getMax() / MB
                        + "\n\t已用大小(M)=" + pool.getUsage().getUsed() / MB
                        + "\n\t已提交(已申请)(M)=" + pool.getUsage().getCommitted() / MB
                        + "\n\t使用率=" + (pool.getUsage().getUsed() * 100 / pool.getUsage().getCommitted()) + "%")
                        .append(System.lineSeparator());

            }
        }
        return joiner.toString();
    }

    public static String threadInfo() {
        StringBuilder joiner = new StringBuilder();
        ThreadMXBean thread = ManagementFactory.getThreadMXBean();
        joiner.append("ObjectName=" + thread.getObjectName()).append(System.lineSeparator());
        joiner.append("仍活动的线程总数=" + thread.getThreadCount()).append(System.lineSeparator());
        joiner.append("峰值=" + thread.getPeakThreadCount()).append(System.lineSeparator());
        joiner.append("线程总数（被创建并执行过的线程总数）=" + thread.getTotalStartedThreadCount()).append(System.lineSeparator());
        joiner.append("当初仍活动的守护线程（daemonThread）总数=" + thread.getDaemonThreadCount()).append(System.lineSeparator());

        //检查是否有死锁的线程存在
        long[] deadlockedIds = thread.findDeadlockedThreads();
        if (deadlockedIds != null && deadlockedIds.length > 0) {
            ThreadInfo[] deadlockInfos = thread.getThreadInfo(deadlockedIds);
            joiner.append("死锁线程信息:").append(System.lineSeparator());
            joiner.append("\t\t线程名称\t\t状态\t\t").append(System.lineSeparator());
            for (ThreadInfo deadlockInfo : deadlockInfos) {
                joiner.append("\t\t" + deadlockInfo.getThreadName() + "\t\t" + deadlockInfo.getThreadState()
                        + "\t\t" + deadlockInfo.getBlockedTime() + "\t\t" + deadlockInfo.getWaitedTime()
                        + "\t\t" + deadlockInfo.getStackTrace().toString())
                        .append(System.lineSeparator());
            }
        }
        long[] threadIds = thread.getAllThreadIds();
        if (threadIds != null && threadIds.length > 0) {
            ThreadInfo[] threadInfos = thread.getThreadInfo(threadIds);
            joiner.append("所有线程信息:").append(System.lineSeparator());
            joiner.append("\t\t线程名称\t\t\t\t\t状态\t\t\t\t\t线程id").append(System.lineSeparator());
            for (ThreadInfo threadInfo : threadInfos) {
                joiner.append("\t\t" + threadInfo.getThreadName() + "\t\t\t\t\t" + threadInfo.getThreadState()
                        + "\t\t\t\t\t" + threadInfo.getThreadId())
                        .append(System.lineSeparator());
            }
        }

        return joiner.toString();
    }

    private static boolean isSunOsMBean(OperatingSystemMXBean operatingSystem) {
        final String className = operatingSystem.getClass().getName();
        return "com.sun.management.OperatingSystem".equals(className)
                || "com.sun.management.UnixOperatingSystem".equals(className);
    }

    @Override
    public CommandResult invoke(CommandContext context) {
        return new CommandResult(jvmInfo());
    }

    @Override
    public Command key() {
        return Command.JVM;
    }
}
