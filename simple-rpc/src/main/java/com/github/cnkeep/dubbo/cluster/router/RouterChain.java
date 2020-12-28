package com.github.cnkeep.dubbo.cluster.router;

import com.github.cnkeep.dubbo.cluster.Router;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.LinkedList;
import java.util.List;
/**
 * @description: 路由责任链
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-28 16:59
 * @version: v0.0.1
 **/
public class RouterChain<T> {
    private List<Router> registerRouters = new LinkedList<>();

    public RouterChain(List<Router> registerRouters) {
        this.registerRouters = registerRouters;
    }

    public void addRouter(Router router){
        this.registerRouters.add(router);
    }

    public List<Invoker<T>> route(List<Invoker<T>> invokers){
        List<Invoker<T>> finalInvokers = invokers;
        for (Router router : registerRouters) {
            finalInvokers = router.route(invokers);
        }
        return finalInvokers;
    }
}
