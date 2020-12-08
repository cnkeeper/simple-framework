package com.github.cnkeep.dubbo.rpc.dubbo;

import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.common.proxy.ProxyFactory;
import com.github.cnkeep.dubbo.rpc.Exporter;
import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.rpc.Protocol;
import com.github.cnkeep.dubbo.rpc.util.ProtocolUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class DubboProtocol implements Protocol {
    private static final int DEFAULT_EXPORT_PORT =20880;
    private ConcurrentMap<String,Exporter> exportedMap = new ConcurrentHashMap<>();
    private ProxyFactory proxyFactory;


    @Override
    public int getDefaultPort() {
        return DEFAULT_EXPORT_PORT;
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {
        String key = ProtocolUtils.genServiceKey(invoker.getInterface().getName(), "", "");
        DubboExpoter expoter = new DubboExpoter(invoker);
        exportedMap.put(key, expoter);
        return expoter;
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        DubboInvoker invoker = new DubboInvoker(type,url);
        return invoker;
    }

    @Override
    public void destroy() {

    }
}
