package com.github.cnkeep.dubbo.remoting;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
    private Header header;
    private Object data;

    @Data
    @Builder
    public static class Header{
        private short magic;
        private short flag;
        private long id;
        private int dataLen;
    }
}
