package com.github.cnkeep.dubbo.rpc;


import com.github.cnkeep.dubbo.common.URL;

import java.util.Map;

public interface Invocation {
    String getServiceName();

    String getMethodName();

    Class<?>[] getParameterTypes();

    Object[] getParameters();

    URL getConsumerUrl();

    Map<String,Object> getAttachments();
}
