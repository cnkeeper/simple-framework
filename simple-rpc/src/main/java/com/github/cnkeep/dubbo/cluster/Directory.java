package com.github.cnkeep.dubbo.cluster;


import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

public interface Directory {
   <T> List<Invoker<T>> list(Invocation invocation);
}
