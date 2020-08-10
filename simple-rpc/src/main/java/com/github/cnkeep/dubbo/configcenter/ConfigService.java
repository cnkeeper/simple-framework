package com.github.cnkeep.dubbo.configcenter;

import java.util.Map;

public interface ConfigService {
    Map<Object,Object> getConfig(String serviceName);
}
