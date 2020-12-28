package com.github.cnkeep.dubbo.cluster;

import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;


public interface Router {

    /**
     * 路由选择
     * @param invokers
     * @return
     */
   <T> List<Invoker<T>> route(List<Invoker<T>> invokers);
}
