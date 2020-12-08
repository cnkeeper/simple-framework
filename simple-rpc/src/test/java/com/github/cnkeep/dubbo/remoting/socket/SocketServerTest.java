package com.github.cnkeep.dubbo.remoting.socket;

import com.github.cnkeep.dubbo.remoting.Channel;
import com.github.cnkeep.dubbo.remoting.ChannelHandler;
import com.github.cnkeep.dubbo.remoting.nio.NioChannel;
import com.github.cnkeep.dubbo.remoting.nio.NioChannelHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class SocketServerTest {

    Map<String, Channel> connectChannels = new ConcurrentHashMap<>();

    ChannelHandler channelHandler = new NioChannelHandler();

    public void bootstrap() throws IOException {
        Selector selector = Selector.open();
        createServer(9001, selector);
        listen(selector);
    }

    private ServerSocket createServer(int port, Selector selector) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        // 获得通道的socket套接字
        ServerSocket server = serverSocketChannel.socket();
        // 将套接字绑定到端口
        InetSocketAddress address = new InetSocketAddress(port);
        server.bind(address);
        // 在选择器中进行注册ssc通道
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        return server;
    }

    private void listen(Selector selector) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        int num = selector.select();
                        if (num <= 0) continue;
                        Set selectedKeys = selector.selectedKeys();
                        Iterator it = selectedKeys.iterator();
                        while (it.hasNext()) {
                            SelectionKey key = (SelectionKey) it.next();
                            handleEvent(selector, key);
                            it.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleEvent(Selector selector, SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            // 接受新连接
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);

            channelHandler.connected(buildChannel(client));
            // 将新连接注册到选择器
            client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            // 读取数据
            try {
                SocketChannel client = (SocketChannel) key.channel();
                ByteBuffer recBuf = ByteBuffer.allocate(1024);
                recBuf.clear();
                int count = client.read(recBuf);
                if (recBuf.position() > 0) {
                    String body = new String(recBuf.array(), 0, count);
                    channelHandler.receive(buildChannel(client),body);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Channel buildChannel(SocketChannel client) {
        return new NioChannel(client);
    }

    public static void main(String[] args) throws IOException {
        SocketServerTest test = new SocketServerTest();
        test.bootstrap();
    }
}
