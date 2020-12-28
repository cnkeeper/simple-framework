package com.github.cnkeep.dubbo.common.util;


import com.github.cnkeep.dubbo.rpc.Invocation;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public final class ReflectionUtils {

    public static final Type[] getReturnTypes(Invocation invocation) {
        Method method = getMethod(invocation);
        if (method != null) {
            return new Type[]{method.getReturnType(), method.getGenericReturnType()};
        }

        return null;
    }

    public static final Class<?> getReturnType(Invocation invocation) {
        Method method = getMethod(invocation);
        if (method != null) {
            return method.getReturnType();
        }

        return null;
    }

    public static final Method getMethod(Invocation invocation) {
        Class<?> aClass = getClass(invocation);
        try {
            return aClass.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static final Class<?> getClass(Invocation invocation) {
        try {
            return Class.forName(invocation.getServiceName(), true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Not found class " + invocation.getServiceName() + ", cause: " + e.getMessage(), e);
        }
    }


    public static final Object getEmptyObject(Class<?> returnType) {
        if (returnType == null) {
            return null;
        }
        if (returnType == boolean.class || returnType == Boolean.class) {
            return false;
        }
        if (returnType == char.class || returnType == Character.class) {
            return '\0';
        }
        if (returnType == byte.class || returnType == Byte.class) {
            return (byte) 0;
        }
        if (returnType == short.class || returnType == Short.class) {
            return (short) 0;
        }
        if (returnType == int.class || returnType == Integer.class) {
            return 0;
        }
        if (returnType == long.class || returnType == Long.class) {
            return 0L;
        }
        if (returnType == float.class || returnType == Float.class) {
            return 0F;
        }
        if (returnType == double.class || returnType == Double.class) {
            return 0D;
        }
        if (returnType.isArray()) {
            return Array.newInstance(returnType.getComponentType(), 0);
        }
        if (returnType.isAssignableFrom(ArrayList.class)) {
            return new ArrayList<>(0);
        }
        if (returnType.isAssignableFrom(HashSet.class)) {
            return new HashSet<>(0);
        }
        if (returnType.isAssignableFrom(HashMap.class)) {
            return new HashMap<>(0);
        }
        if (String.class.equals(returnType)) {
            return "";
        }
        if (returnType.isInterface()) {
            return null;
        }

        return null;
    }
}
