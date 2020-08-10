package com.github.cnkeep.dubbo.registry;

import com.github.cnkeep.dubbo.common.URL;

import java.util.List;

public interface NotifyListener {
    void notify(List<URL> urls);
}