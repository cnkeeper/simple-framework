package com.github.cnkeep.arthas.command.invoker;

import com.github.cnkeep.arthas.command.Command;
import com.github.cnkeep.arthas.command.CommandContext;
import com.github.cnkeep.arthas.command.CommandInvoker;
import com.github.cnkeep.arthas.command.CommandResult;

import java.lang.management.*;

/**
 * @description: 线程信息
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class JstackInvoker implements CommandInvoker {
    public static String jstack() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // 当前线程信息，等价与jstack
        ThreadInfo[] infos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(),
                threadMXBean.isSynchronizerUsageSupported());

        StringBuilder joiner = new StringBuilder();
        for (ThreadInfo threadInfo : infos) {
            long threadCpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
            LockInfo lockInfo = threadInfo.getLockInfo();
            String fullStacktrace = getFullStacktrace(
                    threadInfo,
                    threadCpuTime,
                    lockInfo == null ? 0 : lockInfo.getIdentityHashCode(),
                    (int) threadInfo.getBlockedCount()
            );
            joiner.append(fullStacktrace).append(System.lineSeparator());
        }

//        // 当前方法调用栈
//        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
//        System.out.println(Arrays.toString(stackTraceElementArray));
        return joiner.toString();
    }

    /**
     * 完全从 ThreadInfo 中 copy 过来
     *
     * @param threadInfo           the thread info object
     * @param cpuUsage             will be ignore if cpuUsage < 0 or cpuUsage > 100
     * @param lockIdentityHashCode 阻塞了其他线程的锁的identityHashCode
     * @param blockingThreadCount  阻塞了其他线程的数量
     * @return the string representation of the thread stack
     */
    public static String getFullStacktrace(ThreadInfo threadInfo, long cpuUsage, int lockIdentityHashCode,
                                           int blockingThreadCount) {
        StringBuilder sb = new StringBuilder("\"" + threadInfo.getThreadName() + "\"" + " Id="
                + threadInfo.getThreadId());

        if (cpuUsage >= 0 && cpuUsage <= 100) {
            sb.append(" cpuUsage=").append(cpuUsage).append("%");
        }

        sb.append(" ").append(threadInfo.getThreadState());

        if (threadInfo.getLockName() != null) {
            sb.append(" on ").append(threadInfo.getLockName());
        }
        if (threadInfo.getLockOwnerName() != null) {
            sb.append(" owned by \"").append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
        }
        if (threadInfo.isSuspended()) {
            sb.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            sb.append(" (in native)");
        }
        sb.append('\n');
        int i = 0;
        for (; i < threadInfo.getStackTrace().length; i++) {
            StackTraceElement ste = threadInfo.getStackTrace()[i];
            sb.append("\tat ").append(ste.toString());
            sb.append('\n');
            if (i == 0 && threadInfo.getLockInfo() != null) {
                Thread.State ts = threadInfo.getThreadState();
                switch (ts) {
                    case BLOCKED:
                        sb.append("\t-  blocked on ").append(threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    case WAITING:
                        sb.append("\t-  waiting on ").append(threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    case TIMED_WAITING:
                        sb.append("\t-  waiting on ").append(threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    default:
                }
            }

            for (MonitorInfo mi : threadInfo.getLockedMonitors()) {
                if (mi.getLockedStackDepth() == i) {
                    sb.append("\t-  locked ").append(mi);
                    if (mi.getIdentityHashCode() == lockIdentityHashCode) {
                    }
                    sb.append('\n');
                }
            }
        }
        if (i < threadInfo.getStackTrace().length) {
            sb.append("\t...");
            sb.append('\n');
        }

        LockInfo[] locks = threadInfo.getLockedSynchronizers();
        if (locks.length > 0) {
            sb.append("\n\tNumber of locked synchronizers = ").append(locks.length);
            sb.append('\n');
            for (LockInfo li : locks) {
                sb.append("\t- ").append(li);
                if (li.getIdentityHashCode() == lockIdentityHashCode) {
                    sb.append(" <---- but blocks ").append(blockingThreadCount);
                    sb.append(" other threads!");
                }
                sb.append('\n');
            }
        }
        sb.append('\n');
        return sb.toString().replace("\t", "    ");
    }

    @Override
    public CommandResult invoke(CommandContext context) {
        return new CommandResult(jstack());
    }

    @Override
    public Command key() {
        return Command.JSTACK;
    }
}
