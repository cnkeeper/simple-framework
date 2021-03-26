package com.github.cnkeep.unit;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @description: Test
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
@RunWith(CustomizeRunner.class)
public class CustomizeRunnerTest {

    @Test
    @MethodNameValidator
    @TimeStat
    public void helloTest(){
        System.out.println("invoke hello>>>>>>>>>>>");
    }

    @Test
    @MethodNameValidator
    @TimeStat
    public void hello(){
        System.out.println("invoke hello>>>>>>>>>>>");
    }
}