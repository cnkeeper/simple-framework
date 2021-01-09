package com.github.cnkeep.dubbo.cluster.cluster.failsafe;

import com.github.cnkeep.dubbo.cluster.Cluster;
import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.cluster.cluster.AbstractCluster;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/1/8
 * @version:
 **/
public class FailsafeCluster<T> extends AbstractCluster<T> implements Cluster {
    @Override
    public <T> Invoker<T> doJoin(Directory<T> directory) {
        return new FailsafeClusterInvoker<>(directory);
    }
}
