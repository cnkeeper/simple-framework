package com.github.cnkeep.dubbo.remoting.nio;

import com.github.cnkeep.dubbo.remoting.Channel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class NioChannel implements Channel {
    private SocketChannel socketChannel;
    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    public NioChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public SocketAddress getRemoteAddress() throws IOException {
        return socketChannel.getRemoteAddress();
    }

    @Override
    public boolean isConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributes.put(key,value);
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void send(byte[] data) throws IOException {
        socketChannel.write(ByteBuffer.wrap(data));
    }
}
