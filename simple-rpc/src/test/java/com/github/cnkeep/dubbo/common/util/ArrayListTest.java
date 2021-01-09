package com.github.cnkeep.dubbo.common.util;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/1/8
 * @version:
 **/
public class ArrayListTest {
    @Test
    public void initCapacity(){
        int count = 1_0000;
        Object o = new Object();
        int[] testLine = {1,2,3,4,8,10,14,16,20,30,60,64,70,80,100,128,200,300,500,1000,3000,5000,6000,7000,10000};

        for (int appendLen : testLine) {
            Object[] appendArr = new Object[appendLen];
            Arrays.fill(appendArr,o);
            List<Object> appendList = Arrays.asList(appendArr);

            Stopwatch start = Stopwatch.createStarted();
            for (int i = 0; i < count; i++) {
                ArrayList<Object> list = new ArrayList<>();
                for (Object object : appendList) {
                    list.add(object);
                }
            }
            start.stop();
            long stat1 = start.elapsed(TimeUnit.MICROSECONDS);

            start.reset().start();
            for (int i = 0; i < count; i++) {
                ArrayList<Object> list = new ArrayList<>(appendList.size());
                for (Object object : appendList) {
                    list.add(object);
                }
            }
            long stat2 = start.stop().elapsed(TimeUnit.MICROSECONDS);
            System.out.println(stat1+","+stat2+","+ BigDecimal.valueOf(stat2).divide(BigDecimal.valueOf(stat1),3, RoundingMode.HALF_DOWN));
        }

    }
}
