package com.github.cnkeep.dubbo.cluster;

import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;


public interface Router {

    List<Invoker> route(List<Invoker> invokers);
}
