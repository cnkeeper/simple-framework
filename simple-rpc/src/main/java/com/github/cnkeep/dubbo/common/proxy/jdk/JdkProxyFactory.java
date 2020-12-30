package com.github.cnkeep.dubbo.common.proxy.jdk;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.common.proxy.ProxyFactory;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description: jdk代理工厂
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-28 17:43
 * @version: v0.0.1
 **/
public class JdkProxyFactory implements ProxyFactory {
    /**
     * for consumer
     * @param invoker
     * @param interfaces
     * @param <T>
     * @return
     */
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new InvokerInvocationHandler(invoker));
    }


    /**
     * for provider
     * @param proxy
     * @param type
     * @param url
     * @param <T>
     * @return
     */
    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        return new Invoker<T>() {
            @Override
            public Class<T> getInterface() {
                return (Class<T>) proxy.getClass();
            }

            @Override
            public Result invoke(Invocation invocation) {
                Exception exception = null;
                Object value = null;
                try {
                    Method method = proxy.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    value = method.invoke(proxy, invocation.getParameters());
                } catch (Exception e) {
                    exception = e;
                }

                return new Result(value,null,exception);
            }
        };
    }
}
