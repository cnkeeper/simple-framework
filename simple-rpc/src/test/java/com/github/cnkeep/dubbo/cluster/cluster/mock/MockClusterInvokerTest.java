package com.github.cnkeep.dubbo.cluster.cluster.mock;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.common.util.ReflectionUtils;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/12/24
 * @version:
 **/
public class MockClusterInvokerTest {

    Invocation invocation;
    Invoker invoker;
    EchoService provider = new EchoServiceImpl();
    URL url;
    Object[] parameters = new Object[0];
    Method invokeMethod;

    @Before
    public void init() throws NoSuchMethodException {
        invoker =   new Invoker() {
            @Override
            public Class getInterface() {
                return EchoService.class;
            }

            @Override
            public Result invoke(Invocation invocation) {
                Method method = ReflectionUtils.getMethod(invocation);
                try {
                    Object ret = method.invoke(provider, invocation.getParameters());
                    return Result.newResult(ret,null,null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return Result.newResult(provider.echo((String) invocation.getParameters()[0]),null,null);
            }
        };



        invocation = new Invocation() {
            @Override
            public String getServiceName() {
                return EchoService.class.getName();
            }

            @Override
            public String getMethodName() {
                return invokeMethod.getName();
            }

            @Override
            public Class<?>[] getParameterTypes() {
                return invokeMethod.getParameterTypes();
            }

            @Override
            public Object[] getParameters() {
                return parameters;
            }

            @Override
            public URL getConsumerUrl() {
                return url;
            }

            @Override
            public Map<String, Object> getAttachments() {
                return new HashMap<>();
            }
        };

    }

    @Test
    public void invoke() throws NoSuchMethodException {
        invokeMethod = EchoService.class.getMethod("echo",String.class);
        parameters = new Object[]{"hello"};

        url = new URL();
        url.setParameter("mock","force:return mock-hello");
        MockClusterInvoker<Object> mockClusterInvoker = new MockClusterInvoker<>(null,invoker);

        Result result = mockClusterInvoker.invoke(invocation);

        Assert.assertEquals("mock-hello",result.getValue());
    }

    public static class EchoServiceImpl implements EchoService{

        @Override
        public String echo(String message) {
            return "actual"+message;
        }

        @Override
        public List<Info> getInfos() {
            return null;
        }
    }

    public static class Info{
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }

    public static interface EchoService{
        String echo(String message);

        List<Info> getInfos();
    }
}