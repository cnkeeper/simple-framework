package com.github.cnkeep.dubbo.cluster.cluster.failfast;

import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.LoadBalance;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractClusterInvoker;
import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.RpcException;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

/**
 * @description: 快速失败，抛异常
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/1/8
 * @version: v0.0.1
 **/
public class FailfastInvoker<T> extends AbstractClusterInvoker<T> implements Invoker {
    public  FailfastInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(List<Invoker<T>> invokers, Invocation invocation, LoadBalance loadBalance) {
        Invoker<T> invoker = loadBalance.select(invokers, invocation.getConsumerUrl(), invocation);
        try {
            Result result = invoker.invoke(invocation);
            return result;
        } catch (Exception e) {
            throw new RpcException(e);
        }
    }
}
