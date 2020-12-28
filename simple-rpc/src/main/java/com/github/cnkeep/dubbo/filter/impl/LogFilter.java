package com.github.cnkeep.dubbo.filter.impl;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.filter.Filter;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 日志过滤器
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-28 17:8
 * @version: v0.0.1
 **/
@Slf4j
public class LogFilter implements Filter {
    @Override
    public Result filter(Invoker invoker, Invocation invocation) {
        try {
            return invoker.invoke(invocation);
        } finally {
            // logger
            log.info("[LogFilter] invocation={}",invocation);
        }
    }

    @Override
    public int order() {
        return 0;
    }
}
