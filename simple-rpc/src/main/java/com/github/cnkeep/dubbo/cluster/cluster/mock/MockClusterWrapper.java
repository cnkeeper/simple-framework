package com.github.cnkeep.dubbo.cluster.cluster.mock;

import com.github.cnkeep.dubbo.cluster.Cluster;
import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.rpc.Invoker;


public class MockClusterWrapper implements Cluster {

    private Cluster cluster;

    public MockClusterWrapper(Cluster cluster) {
        this.cluster = cluster;
    }

    @Override
    public <T> Invoker<T> join(Directory<T> directory) {
        return new MockClusterInvoker(directory, cluster.join(directory));
    }
}
