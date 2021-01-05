package com.github.cnkeep.dubbo.common.proxy.javaassist;

import com.github.cnkeep.dubbo.EchoService;
import com.github.cnkeep.dubbo.common.util.ReflectionUtils;
import com.google.common.collect.Lists;
import javassist.*;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2020/12/30
 * @version:
 **/
public class ProxyTest {
    @Test
    public void method() throws NotFoundException, CannotCompileException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class<?>[] its = new Class[]{EchoService.class};

        Proxy proxy = makeProxy(its);
        EchoService service = (EchoService) proxy.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });

        service.echo("hello");
    }

    private static final String PACKAGE_NAME = Proxy.class.getPackage().getName();
    private static final AtomicLong COUNTER = new AtomicLong();
    private static final Map<String,Object> PROXY_CACHE_MAP = new WeakHashMap<>();

    public static Proxy makeProxy(Class<?>[] its) throws CannotCompileException, NotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < its.length; i++) {
            String itf = its[i].getName();
            if (!its[i].isInterface()) {
                throw new RuntimeException(itf + " is not a interface.");
            }

            Class<?> tmp = null;
            try {
                tmp = Class.forName(itf, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e) {
            }

            if (tmp != its[i]) {
                throw new IllegalArgumentException(its[i] + " is not visible from class loader");
            }

            sb.append(itf).append(';');
        }

        // use interface class name list as key.
        String key = sb.toString();
        
        List<String> fieldsBody = Lists.newLinkedList();
        List<String> methodBody = Lists.newLinkedList();
        List<Method> methods = new ArrayList<>();
        List<CtClass> ctClasses = new ArrayList<>();
        fieldsBody.add("public static " + Method.class.getName() + "[] methods;");
        fieldsBody.add("private " + InvocationHandler.class.getName() + " handler;");
        ClassPool classPool = ClassPool.getDefault();

        try {
            for (Class<?> it : its) {
                CtClass ctClass = classPool.get(it.getName());
                ctClasses.add(ctClass);
            }

            Set<String> createdMethod = new HashSet<>();
            for (Class<?> it : its) {
                for (Method method : it.getMethods()) {
                    String desc = ReflectionUtils.getDesc(method);
                    if (createdMethod.contains(desc)
                            && Modifier.isStatic(method.getModifiers())) {
                        continue;
                    }
                    methodBody.add(makeMethod(methods.size(), method).toString());
                    createdMethod.add(desc);
                    methods.add(method);
                }
            }


            String className = PACKAGE_NAME+".Proxy" + COUNTER.getAndIncrement();
            CtClass ctClass = classPool.makeClass(className);
            ctClass.setSuperclass(classPool.get(Proxy.class.getName()));
            for (CtClass aClass : ctClasses) {
                ctClass.addInterface(aClass);
            }

            for (String field : fieldsBody) {
                ctClass.addField(CtField.make(field, ctClass));
            }


            ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
            String cons = "public " + className + " (" + InvocationHandler.class.getName() + " h){handler=$1;}";
            ctClass.addConstructor(CtNewConstructor.make(cons, ctClass));
            String newInstanceBody = "public Object newInstance(" + InvocationHandler.class.getName() + " h){return new " + className + "($1);}";
            ctClass.addMethod(CtMethod.make(newInstanceBody, ctClass));
            for (String body : methodBody) {
                ctClass.addMethod(CtNewMethod.make(body, ctClass));
            }

            Class<?> aClass = ctClass.toClass();
            Field methodsField = aClass.getField("methods");
            methodsField.set(null, methods);

            return (Proxy) aClass.newInstance();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {

        }
    }


    private static StringBuilder makeMethod(int index, Method method) {
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();

        StringBuilder code = new StringBuilder("public ").append(returnType.getName()).append(" ").append(methodName).append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            code.append(parameterTypes[i].getName()).append(" ").append("arg" + i);
            if (i != parameterTypes.length - 1) {
                code.append(",");
            }
        }
        code.append("){");
        code.append("Object[] args = new Object[").append(parameterTypes.length).append("];");
        for (int j = 0; j < parameterTypes.length; j++) {
            code.append(" args[").append(j).append("] = ($w)$").append(j + 1).append(";");
        }

        code.append(" Object ret = handler.invoke(this, methods[").append(index).append("], args);");
        if (!Void.TYPE.equals(returnType)) {
            code.append(" return ").append(Wrapper.arg(returnType, "ret")).append(";");
        }
        code.append("}");
        return code;
    }
}
