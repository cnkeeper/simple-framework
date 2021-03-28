package com.github.cnkeep.stream;

import java.util.function.Consumer;

/**
 * @description: 迭代器
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public interface Spliterator<T> {
    /**
     * 判断是否还有元素需要处理
     *
     * @param action
     * @return
     */
    boolean tryAdvance(Consumer<T> action);

    /**
     * 遍历数据源执行处理
     * @param action
     */
    default void forEachRetaining(Consumer<T> action) {
        do {
        } while (tryAdvance(action));
    }


    /**
     * 获取数据源数据数量
     *
     * @return
     */
    int getExactSizeIfKnown();
}
