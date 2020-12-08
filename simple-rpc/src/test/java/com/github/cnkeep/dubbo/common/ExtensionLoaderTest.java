package com.github.cnkeep.dubbo.common;


import com.github.cnkeep.dubbo.serialize.Deserialize;
import org.junit.Assert;
import org.junit.Test;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/8/10
 * @version:
 **/
public class ExtensionLoaderTest {

    @Test
    public void getExtensionLoaderTest() {
        ExtensionLoader<Deserialize> extensionLoader = ExtensionLoader.getExtensionLoader(Deserialize.class);
        Assert.assertNotNull(extensionLoader);
    }

    @Test
    public void getExtensionTest() {
        ExtensionLoader<Deserialize> extensionLoader = ExtensionLoader.getExtensionLoader(Deserialize.class);
        Assert.assertNotNull(extensionLoader);
        Deserialize deserialize = extensionLoader.getExtension("jackson");
        Assert.assertNotNull(deserialize);
        System.out.println(deserialize);
    }

}