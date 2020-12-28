package com.github.cnkeep.dubbo.cluster.cluster.mock;

import com.github.cnkeep.dubbo.cluster.Directory;
import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.RpcException;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.common.util.ReflectionUtils;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-24 9:7
 * @version:
 **/
public class MockClusterInvoker<T> implements Invoker {
    private final String MOCK_KEY = "mock";
    private final String FORCE_MOCK_MODE = "force";
    private final String PREFIX_THROW = "throw";
    private final String PREFIX_RETURN = "return";
    private Directory directory;
    private Invoker invoker;

    public MockClusterInvoker(Directory<T> directory, Invoker invoker) {
        this.directory = directory;
        this.invoker = invoker;
    }

    @Override
    public Class getInterface() {
        return directory.getInterface();
    }

    @Override
    public Result invoke(Invocation invocation) {
        URL url = invocation.getConsumerUrl();
        String mock = url.getParameter(MOCK_KEY, Boolean.FALSE.toString());
        Result result = null;
        if (Boolean.FALSE.toString().equalsIgnoreCase(mock)) {
            result = invoker.invoke(invocation);
        } else if (mock.startsWith(FORCE_MOCK_MODE)) {
            result = doForceInvoke(mock, invocation);
        } else {
            // fail-back
            try {
                result = invoker.invoke(invocation);
            } catch (Exception e) {
                result = doForceInvoke(mock, invocation);
            }
        }
        return result;
    }

    private Result doForceInvoke(String mock, Invocation invocation) {
        int index = mock.indexOf(":");
        if (index < 0) {
            throw new IllegalArgumentException("force mock illegal args");
        }
        mock = mock.substring(index + 1);
        if (mock.startsWith(PREFIX_RETURN)) {
            Object retV = parseMockReturnValue(mock.substring(PREFIX_RETURN.length()).trim(), ReflectionUtils.getReturnType(invocation));
            return Result.newResult(retV, null, null);
        } else if (mock.startsWith(PREFIX_THROW)) {
            Throwable throwable = getThrowable(mock.substring(PREFIX_THROW.length()).trim());
            throw new RpcException(throwable);
        } else {
            return Result.newResult(null, null, null);
        }
    }

    private Throwable getThrowable(String mockThrowable) {
        try {
            return (Throwable) Class.forName(mockThrowable).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object parseMockReturnValue(String returnStr, Class<?> returnType) {
        if ("empty".equalsIgnoreCase(returnStr)) {
            return ReflectionUtils.getEmptyObject(returnType);
        } else if (returnType.isAssignableFrom(String.class)) {
            return returnStr;
        }

        // TODO: 2020/12/24 复杂类型的解析转换
        return null;
    }
}
