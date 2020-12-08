package com.github.cnkeep.dubbo.remoting;

import java.io.IOException;

/**
 * @description:
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2020/12/7
 * @version:
 **/
public interface ChannelHandler {
    void connected(Channel channel) throws IOException;

    void send(Channel channel,Object message) throws IOException;

    void receive(Channel channel,Object message);
}
