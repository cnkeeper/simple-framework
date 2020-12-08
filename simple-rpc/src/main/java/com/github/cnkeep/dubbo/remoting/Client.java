package com.github.cnkeep.dubbo.remoting;


public interface Client extends Channel {
    boolean send(Object message);
}
