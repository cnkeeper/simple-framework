package com.github.cnkeep.dubbo.filter;

import com.github.cnkeep.dubbo.rpc.Invocation;
import com.github.cnkeep.dubbo.rpc.Invoker;
import com.github.cnkeep.dubbo.common.Result;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


public class FilterChain {
    private List<Filter> filterList = new LinkedList<>();

    public void addFilter(Filter filter) {
        filterList.add(filter);
    }

    public void removeFilter(Filter filter) {
        filterList.remove(filter);
    }

    public Invoker buildFilterChain(Invoker invoker) {
        if (filterList.isEmpty()) {
            return invoker;
        }

        Collections.sort(filterList, new Comparator<Filter>() {
            @Override
            public int compare(Filter o1, Filter o2) {
                return o1.order()-o2.order();
            }
        });

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
}
