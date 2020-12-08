package com.github.cnkeep.dubbo.common.proxy.jdk;

import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.rpc.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/8/17
 * @version:
 **/
public class InvokerInvocationHandler implements InvocationHandler {
    private Invoker invoker;

    public InvokerInvocationHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcInvocation invocation = new RpcInvocation();
        invocation.setServiceName(invoker.getInterface().getName());
        invocation.setMethodName(method.getName());
        invocation.setParameterTypes(method.getParameterTypes());
        invocation.setParameters(args);

        return invoker.invoke(invocation).getValue();
    }
}
