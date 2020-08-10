package com.github.cnkeep.dubbo.rpc;


public interface Invocation {
    String getServiceName();

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getParameters();
}
