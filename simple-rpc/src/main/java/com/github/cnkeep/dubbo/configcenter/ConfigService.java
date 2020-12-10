package com.github.cnkeep.dubbo.configcenter;

import java.util.Map;

public interface ConfigService {
    Map<String,Object> getConfig(String serviceName);
}
