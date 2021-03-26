package com.github.cnkeep.ratelimit;

import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: Java内省
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version: v1.0.0
 **/
public class IntrospectorTest {

    public static class Bean{
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @Test
    public void beanToMap() throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Bean.class);
        System.out.println(beanInfo);
        Bean bean = new Bean();
        Map<String,Object> map = new HashMap<>(16);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            Method readMethod = propertyDescriptor.getReadMethod();
            Object value = readMethod.invoke(bean);
//            System.out.println(propertyDescriptor.getName()+","+value);
            map.put(propertyDescriptor.getDisplayName(),value);
        }

        System.out.println(map);
    }
}
