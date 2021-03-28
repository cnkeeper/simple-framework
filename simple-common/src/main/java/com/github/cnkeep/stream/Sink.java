package com.github.cnkeep.stream;

import java.util.function.Consumer;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public interface Sink<T> extends Consumer<T> {
    /**
     * 遍历之前执行
     *
     * @param size
     */
    default void begin(int size) {
    }

    /**
     * 遍历完成之后执行
     */
    default void end() {
    }

    /**
     * 单个遍历执行
     *
     * @param t
     */
    default void accept(T t) {

    }

    abstract static class ChainSink<T, R> implements Sink<T> {
        protected Sink<R> downstream;

        public ChainSink(Sink<R> downstream) {
            this.downstream = downstream;
        }

        @Override
        public void begin(int size) {
            downstream.begin(size);
        }

        @Override
        public void end() {
            downstream.end();
        }
    }
}
