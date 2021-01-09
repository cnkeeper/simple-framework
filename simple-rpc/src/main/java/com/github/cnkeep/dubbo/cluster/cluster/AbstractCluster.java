package com.github.cnkeep.dubbo.cluster.cluster;

import com.github.cnkeep.dubbo.cluster.Cluster;
import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/1/8
 * @version:
 **/
public abstract class AbstractCluster<T> implements Cluster {
    @Override
    public <T> Invoker<T> join(Directory<T> directory) {
        return doJoin(directory);
    }

    protected abstract <T> Invoker<T> doJoin(Directory<T> directory);
}
