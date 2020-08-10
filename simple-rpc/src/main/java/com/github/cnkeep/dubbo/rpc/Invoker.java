package com.github.cnkeep.dubbo.rpc;


import com.github.cnkeep.dubbo.common.Result;

public interface Invoker<T> {

    Class<T> getInterface();

    Result invoke(Invocation invocation);
}
