package com.github.cnkeep.arthas.command;

/**
 * @description: 命令执行器上下文
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/4/7
 * @version: 1.0
 **/
public class CommandContext {
    private String pid;
    private Object[] args;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
