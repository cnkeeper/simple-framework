package com.github.cnkeep.dubbo.cluster.router;


import com.github.cnkeep.dubbo.cluster.Router;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

public class ConditionRouter implements Router {

    @Override
   public  <T> List<Invoker<T>> route(List<Invoker<T>> invokers){
        return null;
    }
}
