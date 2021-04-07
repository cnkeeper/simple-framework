package com.github.cnkeep.arthas.command;

/**
 * @description: 命令执行结果封装
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/4/7
 * @version: 1.0
 **/
public class CommandResult {
    public Object data;

    public CommandResult() {
    }

    public CommandResult(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static final CommandResult DEFAULT_RESULT = new CommandResult("");

    public static CommandResult defaultResult() {
        return DEFAULT_RESULT;
    }
}
