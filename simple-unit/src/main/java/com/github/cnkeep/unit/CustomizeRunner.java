package com.github.cnkeep.unit;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @description: 自定义Runner
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
public class CustomizeRunner extends BlockJUnit4ClassRunner {
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public CustomizeRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected List<FrameworkMethod> getChildren() {
        Set<FrameworkMethod> methods = new LinkedHashSet<>();
        // 返回所以标记@Test的方法
        methods.addAll(computeTestMethods());

        // 返回所以标记@ITest的方法
        methods.addAll(computeITestMethods());

        return new LinkedList<>(methods);
    }

    private List<FrameworkMethod> computeITestMethods() {
        return getTestClass().getAnnotatedMethods(TimeStat.class);
    }

    @Override
    protected TestClass createTestClass(Class<?> testClass) {
        return super.createTestClass(testClass);
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        super.runChild(method, notifier);
    }


    @Override
    protected boolean isIgnored(FrameworkMethod child) {
        // 判断方法是否忽略
        return super.isIgnored(child);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        Statement statement = super.methodInvoker(method, test);
        if (method.getAnnotation(MethodNameValidator.class) != null) {
            statement = new MethodNameValidatorStatement(statement, method, test);
        }
        if (method.getAnnotation(TimeStat.class) != null) {
            statement = new TimeStatStatement(statement, test);
        }
        return statement;
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
        // @Before注解包装
        return super.withBefores(method, target, statement);
    }

    @Override
    protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
        // @After注解包装
        return super.withAfters(method, target, statement);
    }

    @Override
    protected Statement withBeforeClasses(Statement statement) {
        // @BeforeClass注解包装
        return super.withBeforeClasses(statement);
    }

    @Override
    protected Statement withAfterClasses(Statement statement) {
        // @AfterClass注解包装
        return super.withAfterClasses(statement);
    }

    private void logger() {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        //coz 0th will be getStackTrace so 1st
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        System.out.println(methodName);
    }
}
