package com.github.cnkeep.dubbo.common.util;

import com.github.cnkeep.dubbo.rpc.Invoker;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class ReflectionUtilsTest {
    @Test
    public void genericType(){
        List<Invoker> list = new LinkedList<Invoker>();
        Type[] types = list.getClass().getGenericInterfaces();
        System.out.println(Arrays.toString(types));
    }
}