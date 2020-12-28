package com.github.cnkeep.dubbo.cluster;

import com.github.cnkeep.dubbo.rpc.Invoker;

public interface Cluster {
    <T> Invoker<T> join(Directory<T> directory);
}
