package com.github.cnkeep.arthas.command;

/**
 * @description: 命令执行器接口
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/4/7
 * @version: 1.0
 **/
public interface CommandInvoker {
    /**
     * invoke command
     * @param context
     * @return
     */
    CommandResult invoke(CommandContext context);

    /**
     * key
     *
     * @return
     */
    Command key();
}
