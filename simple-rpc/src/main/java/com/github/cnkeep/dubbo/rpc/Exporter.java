package com.github.cnkeep.dubbo.rpc;


public interface Exporter<T> {
    Invoker<T> getInvoker();

    void unexport();

}