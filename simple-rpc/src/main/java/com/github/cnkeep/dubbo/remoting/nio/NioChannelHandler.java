package com.github.cnkeep.dubbo.remoting.nio;

import com.github.cnkeep.dubbo.remoting.Channel;
import com.github.cnkeep.dubbo.remoting.ChannelHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class NioChannelHandler implements ChannelHandler {
    Map<String, Channel> connectChannels = new ConcurrentHashMap<>();

    public NioChannelHandler() {
        ping();
    }

    @Override
    public void connected(Channel channel) throws IOException {
        connectChannels.put(channel.getRemoteAddress().toString(), channel);
    }

    private void ping() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("connected client size:" + connectChannels.size());
                    for (Channel channel : connectChannels.values()) {
                        send(channel, "ping");
                        System.out.println("ping" + channel.getRemoteAddress());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 10, TimeUnit.SECONDS);

    }

    @Override
    public void send(Channel channel, Object message) throws IOException {
        NioChannel socketChannel = (NioChannel) channel;
        socketChannel.send(message.toString().getBytes());
    }

    @Override
    public void receive(Channel channel, Object message) {
        System.out.println("receiver msg:" + message);
    }
}
