package com.github.cnkeep.dubbo.registry;

import com.github.cnkeep.dubbo.common.URL;

import java.util.List;


public interface RegistryService {

    void register(URL url);


    void unregister(URL url);


    void subscribe(URL url, NotifyListener listener);


    void unsubscribe(URL url, NotifyListener listener);

    List<URL> lookup(URL url);

}
