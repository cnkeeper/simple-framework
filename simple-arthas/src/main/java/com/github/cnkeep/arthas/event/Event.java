package com.github.cnkeep.arthas.event;


public class Event {
   private Class clazz;
   private Object[] args;
   private Class returnType;
   private Object returnValue;
   private String listenId;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public String getListenId() {
        return listenId;
    }

    public void setListenId(String listenId) {
        this.listenId = listenId;
    }
}
