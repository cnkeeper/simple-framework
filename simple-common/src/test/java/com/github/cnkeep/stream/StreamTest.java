package com.github.cnkeep.stream;

import org.junit.Test;

import java.util.function.Consumer;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/27
 * @version:
 **/
public class StreamTest {
    @Test
    public void streamTest() {
        String[] arr = {"1", "1", "2", "3", "4", "5"};
        Spliterator<String> spliterator = new Spliterator<String>() {
            int curIndex = 0;

            @Override
            public boolean tryAdvance(Consumer<String> action) {
                if (curIndex < arr.length) {
                    action.accept(arr[curIndex]);
                    curIndex++;
                    return true;
                }
                return false;
            }

            @Override
            public int getExactSizeIfKnown() {
                return 0;
            }
        };


        StatelessOP<String, String> stream = new StatelessOP<String, String>(spliterator, null) {
            @Override
            Sink<String> opWrapSink(int flags, Sink<String> sink) {
                return new Sink.ChainSink<String, String>(sink) {
                    @Override
                    public void accept(String s) {
                        downstream.accept(s);
                    }
                };
            }
        };

        stream.map(Integer::valueOf)
                .distinct()
                .filter(t -> t < 3)
                .map(t -> t * 10)
                .forEach(System.out::println);
    }
}
