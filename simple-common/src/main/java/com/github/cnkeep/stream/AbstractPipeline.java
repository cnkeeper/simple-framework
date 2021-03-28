package com.github.cnkeep.stream;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public abstract class AbstractPipeline<T, R> {
    /**
     * 上一步操作
     */
    protected AbstractPipeline preStage;
    /**
     * 下一步操作
     */
    protected AbstractPipeline nextStage;
    /**
     * 第几个操作
     */
    protected int depth;
    /**
     * 数据源迭代器
     */
    protected Spliterator source;

    public AbstractPipeline(Spliterator source, AbstractPipeline preStage) {
//        System.out.println(">>>" + preStage+","+this.toString());
        this.source = source;
        this.preStage = preStage;
        if (preStage != null) {
            preStage.nextStage = this;
        }
        depth = (null == preStage ? 0 : preStage.depth) + 1;
    }

    abstract Sink<T> opWrapSink(int flags, Sink<R> sink);


    public void logger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        System.out.println(this.getClass() + "." + stackTrace[2].getMethodName());
    }
}
