package com.github.cnkeep.dubbo.common;

import java.util.Map;

public class Result {
    private Object value;
    private Map<String, Object> attachment;
    private Exception exception;

    public Result() {
    }

    public Result(Object value, Map<String, Object> attachment, Exception exception) {
        this.value = value;
        this.attachment = attachment;
        this.exception = exception;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isSuccess() {
        return exception == null;
    }

    public boolean hasException() {
        return exception != null;
    }
}
