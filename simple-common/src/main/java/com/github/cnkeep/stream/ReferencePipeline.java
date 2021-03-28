package com.github.cnkeep.stream;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public abstract class ReferencePipeline<T, R> extends AbstractPipeline<T, R> implements Stream<R> {

    public ReferencePipeline(Spliterator<R> spliterator, AbstractPipeline preStage) {
        super(spliterator, preStage);
    }

    @Override
    public Stream<R> filter(Predicate<? super R> predicate) {
        return new StatelessOP<R, R>(source, this) {
            @Override
            Sink<R> opWrapSink(int flags, Sink<R> sink) {
                return new Sink.ChainSink<R, R>(sink) {
                    @Override
                    public void accept(R t) {
                        if (predicate.test(t)) {
                            downstream.accept(t);
                        }
                    }
                };
            }
        };
    }

    @Override
    public Stream<R> distinct() {
        return new StatelessOP<R, R>(source, this) {
            @Override
            Sink opWrapSink(int flags, Sink sink) {
                return new Sink.ChainSink<R, R>(sink) {
                    Set<R> set;

                    @Override
                    public void accept(R o) {
                        if (set.add(o)) {
                            downstream.accept(o);
                        }
                    }

                    @Override
                    public void begin(int size) {
                        set = new HashSet<>(size);
                        super.begin(size);
                    }
                };
            }
        };
    }

    @Override
    public <R1> Stream<R1> map(Function<R, R1> mapper) {
        return new StatelessOP<R, R1>(source, this) {
            @Override
            Sink<R> opWrapSink(int flags, Sink<R1> sink) {
                return new Sink.ChainSink<R, R1>(sink) {
                    @Override
                    public void accept(R r) {
                        downstream.accept(mapper.apply(r));
                    }
                };
            }
        };
    }

    @Override
    public void forEach(Consumer<? super R> consumer) {
        Sink<R> sink = new TerminalOP<R>() {
            @Override
            public void accept(R t) {
                consumer.accept(t);
            }
        };

        AbstractPipeline stage = this;
        for (int i = 0; i < depth; i++, stage = stage.preStage) {
            sink = stage.opWrapSink(0, sink);
//            System.out.println(this.preStage);
        }

        sink.begin(0);
        Sink<R> finalSink = sink;
        source.forEachRetaining(t -> finalSink.accept((R) t));
        sink.end();

    }

}
