package com.github.cnkeep.dubbo.cluster.cluster;

import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.LoadBalance;
import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-6 23:19
 * @version:
 **/
public abstract class AbstractClusterInvoker<T> implements Invoker {
    protected Directory<T> directory;

    public AbstractClusterInvoker() {
    }

    public AbstractClusterInvoker(Directory<T> directory) {
        this.directory = directory;
    }

    @Override
    public Class getInterface() {
        return null;
    }

    @Override
    public Result invoke(Invocation invocation) {
        List<Invoker<T>> invokers = list(invocation);
        return doInvoke(invokers,invocation,initLoadBalance(invokers,invocation));
    }

    protected abstract Result doInvoke(List<Invoker<T>> invokers, Invocation invocation, LoadBalance loadBalance);

    protected List<Invoker<T>> list(Invocation invocation) {
        return directory.list(invocation);
    }

    protected LoadBalance initLoadBalance(List<Invoker<T>> invokers, Invocation invocation) {
        return null;
    }
}
