package com.github.cnkeep.dubbo.configcenter.jvm;

import com.github.cnkeep.dubbo.configcenter.ConfigService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class JvmConfigService implements ConfigService {
    private static ConcurrentMap<String,ConcurrentMap<String,Object>> CONFIG_MAP_SERVER = new ConcurrentHashMap<>();
    private static JvmConfigService INSTANCE = new JvmConfigService();

    private JvmConfigService() {
    }

    @Override
    public Map<String, Object> getConfig(String serviceName) {
        return CONFIG_MAP_SERVER.getOrDefault(serviceName,new ConcurrentHashMap<>());
    }

    public static JvmConfigService getInstance(){
        return INSTANCE;
    }
}
