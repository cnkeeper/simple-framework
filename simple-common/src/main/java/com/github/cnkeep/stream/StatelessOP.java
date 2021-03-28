package com.github.cnkeep.stream;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public abstract class StatelessOP<T, R> extends ReferencePipeline<T,R> {

    public StatelessOP(Spliterator<R> spliterator, AbstractPipeline preStage) {
        super(spliterator, preStage);
    }
}
