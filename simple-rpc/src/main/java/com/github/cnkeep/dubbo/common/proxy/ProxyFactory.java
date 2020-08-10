package com.github.cnkeep.dubbo.common.proxy;


import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.rpc.Invoker;

public interface ProxyFactory {
    /**
     * consumer side use
     * @param invoker
     * @param interfaces
     * @param <T>
     * @return
     */
    <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces);


    /**
     * provider side use
     * @param proxy
     * @param type
     * @param url
     * @param <T>
     * @return
     */
    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url);
}
