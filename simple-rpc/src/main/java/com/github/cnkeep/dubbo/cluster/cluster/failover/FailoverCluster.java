package com.github.cnkeep.dubbo.cluster.cluster.failover;

import com.github.cnkeep.dubbo.cluster.Cluster;
import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractCluster;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description: FailoverCluster-重试其他资源
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-6 23:16
 * @version: v0.0.1
 **/
public class FailoverCluster<T> extends AbstractCluster<T> implements Cluster {
    @Override
    public <T> Invoker<T> doJoin(Directory<T> directory) {
        return new FailoverClusterInvoker<>(directory);
    }
}
