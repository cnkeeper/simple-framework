package com.github.cnkeep.dubbo.rpc.dubbo;

import com.github.cnkeep.dubbo.rpc.Exporter;
import com.github.cnkeep.dubbo.rpc.Invoker;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/12/8
 * @version:
 **/
public class DubboExpoter implements Exporter {
    private Invoker invoker;

    public DubboExpoter(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Invoker getInvoker() {
        return invoker;
    }

    @Override
    public void unexport() {
        // TODO
    }
}
