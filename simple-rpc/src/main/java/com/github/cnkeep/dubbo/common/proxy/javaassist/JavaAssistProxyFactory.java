package com.github.cnkeep.dubbo.common.proxy.javaassist;

import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.common.proxy.ProxyFactory;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description: javaassit 代理
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-30 17:20
 * @version:
 **/
public class JavaAssistProxyFactory implements ProxyFactory {
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return null;
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        return null;
    }
}
