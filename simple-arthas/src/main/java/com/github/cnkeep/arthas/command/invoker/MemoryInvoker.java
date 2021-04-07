package com.github.cnkeep.arthas.command.invoker;

import com.github.cnkeep.arthas.command.Command;
import com.github.cnkeep.arthas.command.CommandContext;
import com.github.cnkeep.arthas.command.CommandInvoker;
import com.github.cnkeep.arthas.command.CommandResult;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

/**
 * @description: 内存信息
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/5/12
 * @version: 1.0.0
 **/
public class MemoryInvoker implements CommandInvoker {

    public static String memoryInfo() {
        MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        return heapMemoryUsage.toString();
    }

    @Override
    public CommandResult invoke(CommandContext context) {
        return new CommandResult(memoryInfo());
    }

    @Override
    public Command key() {
        return Command.MEMORY;
    }
}
