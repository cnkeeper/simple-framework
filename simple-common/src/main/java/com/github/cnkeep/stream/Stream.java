package com.github.cnkeep.stream;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public interface Stream<T> {

    Stream<T> filter(Predicate<? super T> predicate);

    void forEach(Consumer<? super T> action);

    <R> Stream<R> map(Function<T, R> mapper);

    Stream<T> distinct();
}
