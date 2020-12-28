package com.github.cnkeep.dubbo.rpc.wrapper;

import com.github.cnkeep.dubbo.common.Result;
import com.github.cnkeep.dubbo.common.URL;
import com.github.cnkeep.dubbo.filter.Filter;
import com.github.cnkeep.dubbo.rpc.Exporter;
import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.rpc.Protocol;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
/**
 * @description: 过滤器装饰器
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-28 16:57
 * @version: v0.0.1
 **/
public class FilterProtocolWrapper implements Protocol {
    private Protocol protocol;
    private List<Filter> filterList = new LinkedList<>();

    public FilterProtocolWrapper(Protocol protocol) {
        this.protocol = protocol;
    }

    public FilterProtocolWrapper(Protocol protocol, List<Filter> filterList) {
        this.protocol = protocol;
        this.filterList = filterList;
    }

    @Override
    public int getDefaultPort() {
        return protocol.getDefaultPort();
    }

    @Override
    public <T> Exporter<T> export(Invoker<T> invoker) {
        return protocol.export(buildFilterChain(invoker));
    }

    @Override
    public <T> Invoker<T> refer(Class<T> type, URL url) {
        return buildFilterChain(protocol.refer(type,url));
    }

    private Invoker buildFilterChain(Invoker invoker) {
        if (filterList.isEmpty()) {
            return invoker;
        }

        Collections.sort(filterList, new Comparator<Filter>() {
            @Override
            public int compare(Filter o1, Filter o2) {
                return o1.order()-o2.order();
            }
        });

        // 套娃0_0方式生成责任链
        int size = filterList.size();
        Invoker last = invoker;
        for (int i = size - 1; i >= 0; i--) {
            Invoker finalLast = last;
            Filter filter = filterList.get(i);
            invoker = new Invoker(){
                @Override
                public Class getInterface() {
                    return finalLast.getInterface();
                }

                @Override
                public Result invoke(Invocation invocation) {
                    return filter.filter(finalLast,invocation);
                }
            };
            last = invoker;
        }

        return last;
    }

    @Override
    public void destroy() {
        protocol.destroy();
    }

    public List<Filter> getFilterList() {
        return filterList;
    }

    public void setFilterList(List<Filter> filterList) {
        this.filterList = filterList;
    }
}
