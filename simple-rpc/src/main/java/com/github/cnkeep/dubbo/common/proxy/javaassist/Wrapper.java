package com.github.cnkeep.dubbo.common.proxy.javaassist;

import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description: 类生成器
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020-12-29 17:55
 * @version: v0.0.1
 **/
public abstract class Wrapper {
    public static Wrapper makeWrapper(Class<?> clazz) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
        String name = clazz.getName();
        StringBuilder m1 = new StringBuilder();
        m1.append("public Object invokeMethod(Object o, String n, Class[] p, Object[] v) throws " + InvocationTargetException.class.getName() + "{ ");
        m1.append(name).append(" w; try{ w = ((").append(name).append(")$1); }catch(Throwable e){ throw new IllegalArgumentException(e); }");
        m1.append("try{");
        Method[] methods = clazz.getMethods();
        for (Method m : methods) {
            //ignore Object's method.
            if (m.getDeclaringClass() == Object.class) {
                continue;
            }

            String mn = m.getName();
            m1.append(" if( \"").append(mn).append("\".equals( $2 ) ");
            int len = m.getParameterTypes().length;
            m1.append(" && ").append(" $3.length == ").append(len);

            boolean override = false;
            for (Method m2 : methods) {
                if (m != m2 && m.getName().equals(m2.getName())) {
                    override = true;
                    break;
                }
            }
            if (override) {
                if (len > 0) {
                    for (int l = 0; l < len; l++) {
                        m1.append(" && ").append(" $3[").append(l).append("].getName().equals(\"")
                                .append(m.getParameterTypes()[l].getName()).append("\")");
                    }
                }
            }

            m1.append(" ) { ");

            if (m.getReturnType() == Void.TYPE) {
                m1.append(" w.").append(mn).append('(').append(args(m.getParameterTypes(), "$4")).append(");").append(" return null;");
            } else {
                m1.append(" return ($w)w.").append(mn).append('(').append(args(m.getParameterTypes(), "$4")).append(");");
            }

            m1.append(" }");
        }
        m1.append(" } catch(Throwable e) { ");
        m1.append("     throw new java.lang.reflect.InvocationTargetException(e); ");
        m1.append(" }");

        m1.append(" throw new " + NoSuchMethodException.class.getName() + "(\"Not found method \\\"\"+$2+\"\\\" in class " + name + ".\"); }");
        m1.append("}");

        System.out.println(m1);
        ClassPool classPool = ClassPool.getDefault();

        CtClass superClass = classPool.get(Wrapper.class.getName());
        CtClass ctClass = classPool.makeClass(name + "$Wrapper");
        ctClass.setSuperclass(superClass);
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));

        CtMethod invokeMethod = CtMethod.make(m1.toString(), ctClass);
        ctClass.addMethod(invokeMethod);

        return (Wrapper) ctClass.toClass().newInstance();
    }

    public static String args(Class<?>[] cs, String name) {
        int len = cs.length;
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(arg(cs[i], name + "[" + i + "]"));
        }
        return sb.toString();
    }

    public static String arg(Class<?> cl, String name) {
        if (cl.isPrimitive()) {
            if (cl == Boolean.TYPE) {
                return "((Boolean)" + name + ").booleanValue()";
            }
            if (cl == Byte.TYPE) {
                return "((Byte)" + name + ").byteValue()";
            }
            if (cl == Character.TYPE) {
                return "((Character)" + name + ").charValue()";
            }
            if (cl == Double.TYPE) {
                return "((Number)" + name + ").doubleValue()";
            }
            if (cl == Float.TYPE) {
                return "((Number)" + name + ").floatValue()";
            }
            if (cl == Integer.TYPE) {
                return "((Number)" + name + ").intValue()";
            }
            if (cl == Long.TYPE) {
                return "((Number)" + name + ").longValue()";
            }
            if (cl == Short.TYPE) {
                return "((Number)" + name + ").shortValue()";
            }
            throw new RuntimeException("Unknown primitive type: " + cl.getName());
        }
        return "(" + getName(cl) + ")" + name;
    }


    protected static String getName(Class<?> c) {
        if (c.isArray()) {
            StringBuilder sb = new StringBuilder();
            do {
                sb.append("[]");
                c = c.getComponentType();
            }
            while (c.isArray());

            return c.getName() + sb.toString();
        }
        return c.getName();
    }

    /**
     * invoke 回调, 通过if/else直接调用原始对象的方法，提高反射效率
     *
     * @param o
     * @param methodName
     * @param paramTypes
     * @param params
     * @return
     * @throws java.lang.reflect.InvocationTargetException
     */
    public abstract Object invokeMethod(Object o, String methodName, Class[] paramTypes, Object[] params) throws java.lang.reflect.InvocationTargetException;

//    public Object invokeMethod(Object $1, String $2, Class[] $3, Object[] $4) throws java.lang.reflect.InvocationTargetException {
//        com.github.cnkeep.dubbo.common.proxy.javaassist.EchoService w;
//        try {
//            w = ((com.github.cnkeep.dubbo.common.proxy.javaassist.EchoService) $1);
//        } catch (Throwable e) {
//            throw new IllegalArgumentException(e);
//        }
//        try {
//            if ("getList".equals($2) && $3.length == 0) {
//                return ($w) w.getList();
//            }
//            if ("echo".equals($2) && $3.length == 0) {
//                return ($w) w.echo();
//            }
//            if ("echo".equals($2) && $3.length == 1 && $3[0].getName().equals("java.lang.String")) {
//                return ($w) w.echo((java.lang.String) $4[0]);
//            }
//            if ("order".equals($2) && $3.length == 1) {
//                return ($w) w.order(((Number) $4[0]).intValue());
//            }
//        } catch (Throwable e) {
//            throw new java.lang.reflect.InvocationTargetException(e);
//        }re
//        throw new NoSuchMethodException("");
//    }
}
