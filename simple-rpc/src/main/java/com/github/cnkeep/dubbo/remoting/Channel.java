package com.github.cnkeep.dubbo.remoting;

import java.io.IOException;
import java.net.SocketAddress;


public interface Channel {
    SocketAddress getRemoteAddress() throws IOException;

    boolean isConnected();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    void removeAttribute(String key);
}
