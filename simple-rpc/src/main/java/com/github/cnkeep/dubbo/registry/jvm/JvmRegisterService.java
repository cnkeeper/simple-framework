package com.github.cnkeep.dubbo.registry.jvm;

import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.registry.NotifyListener;
import com.github.cnkeep.dubbo.registry.RegistryService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * @description: JvmRegisterService
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-14 10:23
 * @version: v0.0.1
 **/
public class JvmRegisterService implements RegistryService {
    private ConcurrentMap<URL, List<URL>> registerMap = new ConcurrentHashMap<>();
    private ConcurrentMap<URL, List<NotifyListener>> listenerMap = new ConcurrentHashMap<>();

    @Override
    public void register(URL url) {
        List<URL> urls = registerMap.getOrDefault(url, new ArrayList<>());
        if(urls.contains(url)){
            return;
        }
        urls.add(url);
        registerMap.put(url, urls);
        doNotify(url);
    }


    @Override
    public void unregister(URL url) {
        List<URL> urls = registerMap.get(url);
        if (urls != null && urls.remove(url)) {
            doNotify(url);
        }
    }

    @Override
    public void subscribe(URL url, NotifyListener listener) {
        List<NotifyListener> listeners = listenerMap.getOrDefault(url, new ArrayList<>());
        listeners.add(listener);
        listenerMap.put(url, listeners);
    }

    @Override
    public void unsubscribe(URL url, NotifyListener listener) {
        List<NotifyListener> listeners = listenerMap.get(url);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    @Override
    public List<URL> lookup(URL url) {
        return registerMap.getOrDefault(url, new ArrayList<>());
    }

    private void doNotify(URL url) {
        List<NotifyListener> listeners = listenerMap.getOrDefault(url, new ArrayList<>());
        for (NotifyListener listener : listeners) {
            listener.notify(lookup(url));
        }
    }
}
