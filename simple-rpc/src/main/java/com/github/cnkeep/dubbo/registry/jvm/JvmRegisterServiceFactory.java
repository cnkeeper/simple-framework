package com.github.cnkeep.dubbo.registry.jvm;

import com.github.cnkeep.dubbo.registry.RegistryService;
import com.github.cnkeep.dubbo.registry.RegistryServiceFactory;


public class JvmRegisterServiceFactory implements RegistryServiceFactory {
    @Override
    public RegistryService getRegistryService() {
        return new JvmRegisterService();
    }
}
