package com.github.cnkeep.simple_springmvc.test.reflect;

import com.github.cnkeep.simple_springmvc.annotation.RequestParam;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/9
 */
public class ClassTest {

    @Test
    public void test1() throws NoSuchMethodException {
        Method addMethod = AnnotationClass.class.getMethod("add", int.class, int.class);
        Class<?> returnType = addMethod.getReturnType();
        System.out.println(returnType);

        Parameter[] parameters = addMethod.getParameters();
        System.out.println(Arrays.toString(parameters));
        for (Parameter parameter : parameters) {
            /**
             * 因为编辑器问题无法获取参数名，只能是arg1,arg2
             */
            String name = parameter.getName();
            Class<?> parameterType = parameter.getType();
            Annotation[] annotations = parameter.getAnnotations();
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if(null != requestParam){
                System.out.println(String.format("name=%s, require=%s", requestParam.name(),requestParam.required()));
            }
        }
    }
}
