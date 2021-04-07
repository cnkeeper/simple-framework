package com.github.cnkeep.arthas.command.invoker;

import com.github.cnkeep.arthas.command.Command;
import com.github.cnkeep.arthas.command.CommandContext;
import com.github.cnkeep.arthas.command.CommandInvoker;
import com.github.cnkeep.arthas.command.CommandResult;
import com.sun.management.HotSpotDiagnosticMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;

/**
 * @description: dump生成内存快照
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class DumpInvoker implements CommandInvoker {
    public static final void dump(String pid) {
        String dumpFile = String.format("./%s_%d_dump.hprof", pid, System.currentTimeMillis());
        HotSpotDiagnosticMXBean hotSpotDiagnosticMXBean = ManagementFactory
                .getPlatformMXBean(HotSpotDiagnosticMXBean.class);
        try {
            hotSpotDiagnosticMXBean.dumpHeap(dumpFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommandResult invoke(CommandContext context) {
        dump(context.getPid());
        return CommandResult.defaultResult();
    }

    @Override
    public Command key() {
        return Command.DUMP;
    }
}
