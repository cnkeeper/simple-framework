package com.github.cnkeep.dubbo.common;

import java.util.Map;

public class Result<T> {
    public int rCode;
    private T data;
    private String message;
    private Map<Object,Object> attachment;
}
