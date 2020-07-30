package com.github.cnkeep.simple_springmvc;

/**
 * 描述: 解析器接口
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/9/6
 */
public interface Resolver {
    /**
     * 返回顺序号，顺序号用户标识其加载顺序
     * @return
     */
    int getOrder();
}
