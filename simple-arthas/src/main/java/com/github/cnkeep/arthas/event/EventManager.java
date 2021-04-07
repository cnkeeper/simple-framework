package com.github.cnkeep.arthas.event;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EventManager {
    private ConcurrentMap<String,EventListener> registedEventListener = new ConcurrentHashMap<>();
    private static EventManager instance;


    public void registerEventListener(String listenId,EventListener eventListener){
        registedEventListener.put(listenId,eventListener);
    }

    public EventListener getEventListener(String listenId){
        return registedEventListener.get(listenId);
    }

    public static EventManager getInstance(){
        if(instance==null){
            synchronized (EventManager.class){
                if(instance==null){
                    instance =  new EventManager();
                }
            }
        }
        return instance;
    }
}
