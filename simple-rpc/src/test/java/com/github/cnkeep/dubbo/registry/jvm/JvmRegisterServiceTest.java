package com.github.cnkeep.dubbo.registry.jvm;

import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.registry.NotifyListener;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class JvmRegisterServiceTest {
    JvmRegisterService jvmRegisterService;

    @Before
    public void init(){
        jvmRegisterService = new JvmRegisterService();
    }

    @Test
    public void test(){
        URL url = new URL();
        url.setPath("path");

        jvmRegisterService.subscribe(url, new NotifyListener() {
            @Override
            public void notify(List<URL> urls) {
                System.out.println(urls);
            }
        });

        jvmRegisterService.register(url);
        jvmRegisterService.register(url);
        jvmRegisterService.register(url);
        jvmRegisterService.register(url);
    }
}