package com.github.cnkeep.dubbo.filter;


import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

public interface Filter {
    Result filter(Invoker invoker, Invocation invocation);

    int order();
}
