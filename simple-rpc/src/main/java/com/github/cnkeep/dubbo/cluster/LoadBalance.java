package com.github.cnkeep.dubbo.cluster;

;

import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

public interface LoadBalance {
    <T> Invoker<T> select(List<Invoker<T>> invokers);
}
