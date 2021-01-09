package com.github.cnkeep.dubbo.cluster.cluster.failsafe;

import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.LoadBalance;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractClusterInvoker;
import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.RpcException;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/1/8
 * @version:
 **/
@Slf4j
public class FailsafeClusterInvoker<T> extends AbstractClusterInvoker<T> {
    public FailsafeClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(List<Invoker<T>> invokers, Invocation invocation, LoadBalance loadBalance) {
        Invoker<T> invoker = loadBalance.select(invokers, invocation.getConsumerUrl(), invocation);
        try {
            Result result = invoker.invoke(invocation);
            return result;
        } catch (Exception e) {
            log.error("",e);
            return Result.newResult(null,null,null);
        }
    }
}
