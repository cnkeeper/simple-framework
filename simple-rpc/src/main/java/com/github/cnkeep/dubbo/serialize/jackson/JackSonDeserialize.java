package com.github.cnkeep.dubbo.serialize.jackson;

import com.github.cnkeep.dubbo.serialize.Deserialize;
import com.github.cnkeep.dubbo.serialize.Serialize;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/8/10
 * @version:
 **/
public class JackSonDeserialize implements Deserialize {
    private Serialize serialize;
    public void setJackson(Serialize serialize){
        this.serialize = serialize;
    }
}
