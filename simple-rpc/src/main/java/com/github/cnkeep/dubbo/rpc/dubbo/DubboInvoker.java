package com.github.cnkeep.dubbo.rpc.dubbo;


import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.remoting.Client;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

import java.util.Map;

public class DubboInvoker<T> implements Invoker<T> {
    private URL url;
    private Map<String,Object> attachments;
    private Class<T> type;
    private Client client;

    public DubboInvoker(Class<T> type,URL url) {
        this.url = url;
        this.type = type;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    public Result invoke(Invocation invocation){
        // TODO client调用远程服务
        client.send(invocation);
        return null;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Map<String, Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String, Object> attachments) {
        this.attachments = attachments;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
