package com.github.cnkeep.dubbo.cluster.cluster.failover;

import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.LoadBalance;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractClusterInvoker;
import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.RpcException;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: 故障转移，重试其他invoker, 直到达到最大重试次数
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-8 0:4
 * @version: 0.0.1
 **/
public class FailoverClusterInvoker<T> extends AbstractClusterInvoker<T> implements Invoker {
    private Directory<T> directory;

    public FailoverClusterInvoker(Directory<T> directory) {
        this.directory = directory;
    }

    @Override
    public Class getInterface() {
        return null;
    }

    @Override
    protected Result doInvoke(List<Invoker<T>> invokers, Invocation invocation, LoadBalance loadBalance) {
        List<Invoker<T>> selected = new ArrayList<>();
        Invoker<T> invoker = select(loadBalance, invocation, invokers, selected);
        selected.add(invoker);

        Exception exception = null;
        int retry = 3;
        for (int i = 0; i < retry; i++) {
            try {
                Result result = invoker.invoke(invocation);
                return result;
            } catch (Exception e) {
                invoker = select(loadBalance, invocation, invokers, selected);
                selected.add(invoker);
                exception = new RpcException(e);
            }
        }

        return Result.newResult(null, null, exception);
    }

    private Invoker<T> select(LoadBalance loadBalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected) {
        List<Invoker<T>> invokersCopy = new ArrayList<Invoker<T>>();
        for (Invoker<T> invoker : invokers) {
            if (selected.contains(invoker)) {
                continue;
            }
            invokersCopy.add(invoker);
        }

        if (invokersCopy.isEmpty()) {
            return selected.get(ThreadLocalRandom.current().nextInt(0, selected.size()));
        }
        return loadBalance.select(invokersCopy, directory.getConsumerUrl(), invocation);
    }
}
