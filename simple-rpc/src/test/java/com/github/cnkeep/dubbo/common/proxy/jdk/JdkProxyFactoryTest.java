package com.github.cnkeep.dubbo.common.proxy.jdk;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.proxy.ProxyFactory;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.*;


public class JdkProxyFactoryTest {
    private ProxyFactory proxyFactory = new JdkProxyFactory();
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

    public <T> List<Invoker> registry_subscribe(Class<T> type) {
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
    public <T> Invoker<T> loadbalance_select(List<Invoker> invokers) {
        // random
        return invokers.isEmpty() ? null : invokers.get(new Random().nextInt(invokers.size()));
    }

    /**
     * Directory查找可用路由
     *
     * @param type
     * @param <T>
     * @return
     */
    public <T> List<Invoker> directory_list(Class<T> type) {
        List<Invoker> invokerList = registry_subscribe(type);

        return router_route(invokerList);
    }

    /**
     * 路由
     * @param invokerList
     * @param <T>
     * @return
     */
    public <T> List<Invoker> router_route(List<Invoker> invokerList){
        List<Invoker> routerList = invokerList.subList(0, Math.min(3, invokerList.size()));
        return routerList;
    }

    public <T> T protocol_reference(Class<T> type) {
        Invoker<T> clusterInvoker = getInvoker(type);
        return proxyFactory.getProxy(clusterInvoker, new Class[]{type});
    }

    private <T> Invoker<T> getInvoker(Class<T> type) {
        return newDubboInvoker(type);
    }


    private <T> Invoker<T> newDubboInvoker(Class<T> type) {
        return new Invoker<T>() {
                @Override
                public Class<T> getInterface() {
                    return type;
                }

                @Override
                public Result invoke(Invocation invocation) {
                    List<Invoker> invokers = directory_list(type);
                    Invoker<T> invoker = loadbalance_select(invokers);
                    return invoker.invoke(invocation);
                }
            };
    }


    @Test
    public void getProxy() {
        IConsumer reference = protocol_reference(IConsumer.class);
        for (int i = 0; i < 10; i++) {
            System.out.println(reference.echo("hello"));
        }
    }

    private interface IConsumer {
        String echo(String message);
    }
}
