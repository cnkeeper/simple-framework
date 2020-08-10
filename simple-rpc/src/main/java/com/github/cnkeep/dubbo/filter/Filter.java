package com.github.cnkeep.dubbo.filter;


import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.common.Result;

public interface Filter {
    <T> Result<T> filter(Invoker invoker, Invocation invocation);

    int order();
}
