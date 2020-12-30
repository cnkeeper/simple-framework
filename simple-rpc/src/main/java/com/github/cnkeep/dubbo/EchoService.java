package com.github.cnkeep.dubbo;


import java.util.List;

public interface EchoService {
    String echo();

    String echo(String message);

    List<String> getList();

    int order(int order);

    void print();
}
