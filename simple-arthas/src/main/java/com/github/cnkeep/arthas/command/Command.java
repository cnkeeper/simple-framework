package com.github.cnkeep.arthas.command;

/**
 * @description: 支持命令枚举
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/4/7
 * @version: 1.0.0
 **/
public enum Command {
    DUMP("dump"),
    JSTACK("jstack"),
    JVM("jvm"),
    MEMORY("memory");

    private String key;

    Command(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static Command lookup(String key, Command defaultV) {
        for (Command command : Command.values()) {
            if (command.key.equalsIgnoreCase(key)) {
                return command;
            }
        }

        return defaultV;
    }
}
