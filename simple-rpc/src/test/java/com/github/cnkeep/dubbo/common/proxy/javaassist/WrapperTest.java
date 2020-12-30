package com.github.cnkeep.dubbo.common.proxy.javaassist;

import com.github.cnkeep.dubbo.EchoService;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * @description: WrapperTest
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2020/12/30
 * @version: v0.0.1
 **/
public class WrapperTest {

    @Test
    public void invokeMethod() throws CannotCompileException, InstantiationException, NotFoundException, IllegalAccessException, InvocationTargetException {
        Wrapper wrapper = Wrapper.makeWrapper(EchoService.class);

        EchoService echoServiceImpl = new EchoService() {

            @Override
            public String echo() {
                return "pong";
            }

            @Override
            public String echo(String message) {
                return "pong " + message;
            }

            @Override
            public List<String> getList() {
                return Arrays.asList("1", "2");
            }

            @Override
            public int order(int order) {
                return 100;
            }

            @Override
            public void print() {
                System.out.println("this class:"+this.getClass());
            }
        };

        String result = (String) wrapper.invokeMethod(echoServiceImpl, "print", new Class[0], new Object[0]);
        System.out.println(result);
    }
}