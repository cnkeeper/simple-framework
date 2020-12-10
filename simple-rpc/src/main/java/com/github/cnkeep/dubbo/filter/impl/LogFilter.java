package com.github.cnkeep.dubbo.filter.impl;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.filter.Filter;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;


public class LogFilter implements Filter {
    @Override
    public Result filter(Invoker invoker, Invocation invocation) {
        try {
            return invoker.invoke(invocation);
        } finally {
            // logger
        }
    }

    @Override
    public int order() {
        return 0;
    }
}
