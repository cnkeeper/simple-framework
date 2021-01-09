package com.github.cnkeep.dubbo.cluster.directiory;


import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.registry.RegistryService;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.List;

public class RegistryDirectory<T> implements Directory<T> {

    private RegistryService registryService;

    @Override
    public Class getInterface() {
        return null;
    }

    @Override
    public List<Invoker<T>> list(Invocation invocation) {
        return null;
    }

    @Override
    public URL getConsumerUrl() {
        return null;
    }
}
