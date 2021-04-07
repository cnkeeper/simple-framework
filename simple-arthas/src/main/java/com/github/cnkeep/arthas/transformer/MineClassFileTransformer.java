package com.github.cnkeep.arthas.transformer;

import com.github.cnkeep.arthas.spy.SpyHandler;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @description: 自定义类转换器
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/9/17
 * @version: 1.0
 **/
public class MineClassFileTransformer implements ClassFileTransformer {
    private Instrumentation inst;
    private String listenId;

    public MineClassFileTransformer(Instrumentation inst) {
        this.inst = inst;
    }

    public MineClassFileTransformer(Instrumentation inst, String listenId) {
        this.inst = inst;
        this.listenId = listenId;
    }

    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        if (!matchClass(className)) {
            return classfileBuffer;
        }

        return genTransformByteData(className, classfileBuffer, transformed);
    }

    private byte[] genTransformByteData(String className, byte[] classfileBuffer, byte[] transformed) {
        System.out.println("Transforming " + className);
        ClassPool pool = null;
        CtClass cl = null;
        try {
            pool = ClassPool.getDefault();

            cl = pool.makeClass(new java.io.ByteArrayInputStream(
                    classfileBuffer));

            if (cl.isInterface() == false) {
                CtMethod[] methods = cl.getDeclaredMethods();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        AOPInsertMethod(methods[i]);
                    }
                }
                transformed = cl.toBytecode();
                cl.debugWriteFile("/tmp");
            }
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className
                    + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    /**
     * FIXME 这里暂时写死只处理固定开头的class
     * @param className
     * @return
     */
    private boolean matchClass(String className) {
        return className.startsWith("test");
    }

    private void AOPInsertMethod(CtMethod method) throws NotFoundException, CannotCompileException {
        if (method.getReturnType() != CtPrimitiveType.voidType) {
            addAfterHasReturn(method);
            System.out.println("insert method invoke");
        }else {
            addAfterNoReturn(method);
        }
    }

    private void addAfterNoReturn(CtMethod method) throws CannotCompileException {
        StringBuilder insertBody = new StringBuilder();
        insertBody.append("")
                .append("{")
                .append("   String listenId=").append("\"").append(this.listenId).append("\";")
                .append("   "+spyHandlerClassPath()).append(".invoke($class, $args, Void.class, null,listenId);")
                .append("}");

        method.insertAfter(insertBody.toString(), false);
    }

    private void addAfterHasReturn(CtMethod method) throws CannotCompileException {
        StringBuilder insertBody = new StringBuilder();
        insertBody.append("")
                .append("{")
                .append("   String listenId=").append("\"").append(this.listenId).append("\";")
                .append("   return ($r)").append(spyHandlerClassPath()).append(".invoke($class, $args, $type, $_,listenId);")
                .append("}");

        method.insertAfter(insertBody.toString(), false);
    }

    private static String spyHandlerClassPath(){
        return SpyHandler.class.getName();
    }
}
