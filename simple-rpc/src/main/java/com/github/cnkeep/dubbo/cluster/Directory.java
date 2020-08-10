package com.github.cnkeep.dubbo.cluster;


import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.common.URL;

import java.util.List;

public interface Directory {
    <T> void join(Invoker<T> invoker);

    <T> List<Invoker<T>> lookup(URL url);
}
