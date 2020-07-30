package com.github.cnkeep.simple_springmvc.test.packagescan;

import com.github.cnkeep.simple_springmvc.util.ClassScan;
import org.junit.Test;

/**
 * 描述: 测试包扫描工具
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/29
 */
public class PackageScanTest {

    @Test
    public void testSrcPackage(){
        Class<?>[] classes = ClassScan.scanPackage("com.github.time69.simple_springmvc");

        for (Class<?> clazz:classes){
            System.out.println(clazz);
        }
        System.out.println(String.format("size=%d",classes.length));
    }
}
