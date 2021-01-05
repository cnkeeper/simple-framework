/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.cnkeep.dubbo.common.proxy.javaassist;


import com.github.cnkeep.dubbo.common.util.ReflectionUtils;
import com.google.common.collect.Lists;
import javassist.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @description: javaassist动态代理生成
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021-1-5 23:35
 * @version: v0.0.1
 **/
public abstract class Proxy {
    public static final InvocationHandler RETURN_NULL_INVOKER = (proxy, method, args) -> null;
    public static final InvocationHandler THROW_UNSUPPORTED_INVOKER = new InvocationHandler() {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            throw new UnsupportedOperationException("Method [" + method.getName() + "] unimplemented.");
        }
    };
    private static final AtomicLong PROXY_CLASS_COUNTER = new AtomicLong(0);
    private static final String PACKAGE_NAME = Proxy.class.getPackage().getName();
    private static final Map<ClassLoader, Map<String, Object>> PROXY_CACHE_MAP = new WeakHashMap<ClassLoader, Map<String, Object>>();
    private static int MAX_PROXY_COUNT = 10;


    protected Proxy() {
    }

    /**
     * Get proxy.
     *
     * @param ics interface class array.
     * @return Proxy instance.
     */
    public static Proxy getProxy(Class<?>... ics) {
        return getProxy(null, ics);
    }

    /**
     * Get proxy.
     *
     * @param cl  class loader.
     * @param its interface class array.
     * @return Proxy instance.
     */
    public static Proxy getProxy(ClassLoader cl, Class<?>... its) {
        String key = getProxyCacheKey(cl, its);
        Map<String, Object> cache = null;
        synchronized (PROXY_CACHE_MAP) {
            cache = PROXY_CACHE_MAP.computeIfAbsent(cl, k -> new HashMap<>());
        }

        Proxy proxy = (Proxy) cache.get(key);
        if (proxy != null) {
            return proxy;
        }

        synchronized (cache) {
            proxy = (Proxy) cache.get(key);
            if (proxy != null) {
                return proxy;
            }
            return doCreateProxy(cl, its);
        }
    }


    private static Proxy doCreateProxy(ClassLoader cl, Class<?>[] its) {
        List<String> fieldsBody = Lists.newLinkedList();
        List<String> methodBody = Lists.newLinkedList();
        List<Method> methods = new ArrayList<>();
        List<CtClass> ctClasses = new ArrayList<>();
        fieldsBody.add("public static " + Method.class.getName() + "[] methods;");
        fieldsBody.add("private " + InvocationHandler.class.getName() + " handler;");
        ClassPool classPool = getClassPool(cl);

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
                    methodBody.add(createMethodBody(methods.size(), method).toString());
                    createdMethod.add(desc);
                    methods.add(method);
                }
            }


            String className = PACKAGE_NAME + ".Proxy" + PROXY_CLASS_COUNTER.getAndIncrement();
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

    /**
     * classPool
     *
     * @param cl
     * @return
     */
    private static ClassPool getClassPool(ClassLoader cl) {
        ClassPool pool = null;
        if (cl == null) {
            pool = ClassPool.getDefault();
        } else {
            pool = new ClassPool(true);
            pool.appendClassPath(new LoaderClassPath(cl));
        }
        return pool;
    }

    private static String getProxyCacheKey(ClassLoader cl, Class<?>[] its) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < its.length; i++) {
            String itf = its[i].getName();
            if (!its[i].isInterface()) {
                throw new RuntimeException(itf + " is not a interface.");
            }

            Class<?> tmp = null;
            try {
                tmp = Class.forName(itf, false, cl);
            } catch (ClassNotFoundException e) {
            }

            if (tmp != its[i]) {
                throw new IllegalArgumentException(its[i] + " is not visible from class loader");
            }

            sb.append(itf).append(';');
        }

        // use interface class name list as key.
        return sb.toString();
    }

    private static StringBuilder createMethodBody(int index, Method method) {
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

    /**
     * get instance with default handler.
     *
     * @return instance.
     */
    public Object newInstance() {
        return newInstance(THROW_UNSUPPORTED_INVOKER);
    }

    /**
     * @param handler
     * @return
     */
    public abstract Object newInstance(InvocationHandler handler);
}
