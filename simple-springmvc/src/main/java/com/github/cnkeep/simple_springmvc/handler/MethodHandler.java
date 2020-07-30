package com.github.cnkeep.simple_springmvc.handler;

import com.github.cnkeep.simple_springmvc.Handler;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 描述: 方法处理器，请求需要调用方法来处理，实际上就是controller中对应的映射方法<br/>
 * {@see org.springframework.web.method.HandlerMethod}
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
@Data
public class MethodHandler implements Handler {
    private Class<?> classType;
    private Object beanInstance;
    private Method method;
    private MethodParameter[] methodParameters;

    public <T extends Annotation> T getAnnotation(Class<T> classType) {
        T annotation = null;
        if (null != method) {
            annotation = method.getAnnotation(classType);
        }
        return annotation;
    }

    public Object getBeanInstance(){
        try {
            return classType.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
