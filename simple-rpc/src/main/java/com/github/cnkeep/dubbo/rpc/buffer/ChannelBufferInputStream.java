package com.github.cnkeep.dubbo.rpc.buffer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description: TODO ChannelBufferInputStream
 * @author: <a href="mailto:zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @date: 2021/1/13
 * @version:
 **/
public class ChannelBufferInputStream extends InputStream {
    private ChannelBuffer buffer;

    @Override
    public int read() throws IOException {
        return -1;
    }
}
