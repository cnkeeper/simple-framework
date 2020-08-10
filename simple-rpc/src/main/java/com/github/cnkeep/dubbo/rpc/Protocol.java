package com.github.cnkeep.dubbo.rpc;

import com.github.cnkeep.dubbo.common.URL;

public interface Protocol {
    int getDefaultPort();

    <T> Exporter<T> export(Invoker<T> invoker);

    <T> Invoker<T> refer(Class<T> type, URL url);

    void destroy();
}