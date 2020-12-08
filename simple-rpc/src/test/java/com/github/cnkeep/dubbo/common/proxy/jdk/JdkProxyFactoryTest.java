package com.github.cnkeep.dubbo.common.proxy.jdk;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;


public class JdkProxyFactoryTest {
    private JdkProxyFactory jdkProxyFactory = new JdkProxyFactory();
    private Map<String, List<Invoker>> registerServer = new HashMap<>();
    private Map<String, Invoker> exportedMap = new HashMap<>();


    @Before
    public void init() {
        Invoker<IConsumer> invoker = createProvider();
        openServer(20880);
        export(invoker);
        register(invoker);
    }

    private Invoker<IConsumer> createProvider() {
        IConsumer provider = new IConsumer() {
            @Override
            public String echo(String message) {
                return "return:" + message;
            }
        };

        return new Invoker<IConsumer>() {
            @Override
            public Class<IConsumer> getInterface() {
                return IConsumer.class;
            }

            @Override
            public Result invoke(Invocation invocation) {
                Exception ex = null;
                try {
                    Method method = getInterface().getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    Object value = method.invoke(provider, invocation.getParameters());
                    return new Result(value, null, null);
                } catch (Exception e) {
                    ex = e;
                }

                return new Result(null, null, ex);
            }
        };
    }

    private void register(Invoker<IConsumer> invoker) {
        List<Invoker> invokerList = registerServer.get(invoker.getInterface().getName());
        if (invokerList == null) {
            invokerList = new ArrayList<>();
        }
        invokerList.add(invoker);
        registerServer.put(invoker.getInterface().getName(), invokerList);
    }

    public <T> List<Invoker> subscribe(Class<T> type) {
        return registerServer.get(type.getName());
    }

    private void export(Invoker<IConsumer> invoker) {
        exportedMap.put(invoker.getInterface().getName(),invoker);
    }

    private void openServer(int port) {
        System.out.println("start provider on :" + port);
    }

    /**
     * 负载均衡
     *
     * @param invokers
     * @param <T>
     * @return
     */
    public <T> Invoker<T> select(List<Invoker> invokers) {
        // random
        return invokers.isEmpty() ? null : invokers.get(new Random().nextInt(invokers.size()));
    }

    /**
     * 路由
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T> List<Invoker> router(Class<T> type) {
        List<Invoker> invokerList = subscribe(type);
        List<Invoker> routerList = invokerList.subList(0, Math.min(3, invokerList.size()));
        return routerList;
    }


    public <T> T reference(Class<T> type) {
        Invoker<T> clusterInvoker = new Invoker<T>() {
            @Override
            public Class<T> getInterface() {
                return type;
            }

            @Override
            public Result invoke(Invocation invocation) {
                List<Invoker> invokers = router(type);
                Invoker<T> invoker = select(invokers);
                return invoker.invoke(invocation);
            }
        };

        return jdkProxyFactory.getProxy(clusterInvoker, new Class[]{type});
    }


    @Test
    public void getProxy() {
        IConsumer reference = reference(IConsumer.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(reference.echo("hello"));
        }
    }

    private interface IConsumer {
        String echo(String message);
    }
}
