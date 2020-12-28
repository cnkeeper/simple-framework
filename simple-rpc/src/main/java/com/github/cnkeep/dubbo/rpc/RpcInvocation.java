package com.github.cnkeep.dubbo.rpc;

import com.github.cnkeep.dubbo.common.URL;

import java.util.Map;
/**
 * @description: rpc调用的参数包装
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-28 17:10
 * @version: v0.0.1
 **/
public class RpcInvocation implements Invocation {
    private String serviceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public URL getConsumerUrl() {
        return null;
    }

    @Override
    public Map<String, Object> getAttachments() {
        return null;
    }
}
