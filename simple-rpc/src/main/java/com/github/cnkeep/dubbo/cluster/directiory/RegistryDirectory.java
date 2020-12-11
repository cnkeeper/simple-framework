package com.github.cnkeep.dubbo.cluster.directiory;


import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.registry.RegistryService;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

public class RegistryDirectory implements Directory {

    private RegistryService registryService;

    /**
     * TODO
     * @param invocation
     * @param <T>
     * @return
     */
    @Override
    public <T> List<Invoker<T>> list(Invocation invocation) {
        return null;
    }
}
