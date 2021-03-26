package com.github.cnkeep.unit;

import org.junit.runners.model.Statement;

/**
 * @description: 统计方法耗时
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-04-08
 * @version:
 **/
public class TimeStatStatement extends Statement {
    private final Statement next;

    private final Object target;

    public TimeStatStatement(Statement next, Object target) {
        this.next = next;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        long start = System.currentTimeMillis();
        next.evaluate();
        System.out.println("use time: " + (System.currentTimeMillis() - start) + "ms");
    }
}
