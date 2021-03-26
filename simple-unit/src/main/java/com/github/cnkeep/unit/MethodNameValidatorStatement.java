package com.github.cnkeep.unit;

import org.junit.Assert;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
public class MethodNameValidatorStatement extends Statement {
    private Statement next;
    private FrameworkMethod method;
    private Object target;

    public MethodNameValidatorStatement(Statement next, FrameworkMethod method, Object target) {
        this.next = next;
        this.method = method;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        validator();
        next.evaluate();
    }

    private void validator() {
        Assert.assertTrue("Method:"+this.method.getMethod()+" not end with Test!!!",this.method.getName().endsWith("Test"));
    }
}
