package com.github.cnkeep.dubbo.cluster.cluster.failfast;

import com.github.cnkeep.dubbo.cluster.Cluster;
import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractCluster;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description: 快速失败，抛异常
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-8 0:3
 * @version: v0.0.1
 **/
public class FailfastCluster<T> extends AbstractCluster<T> implements Cluster {
    @Override
    public <T> Invoker<T> doJoin(Directory<T> directory) {
        return new FailfastInvoker(directory);
    }
}
