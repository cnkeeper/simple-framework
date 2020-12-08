package com.github.cnkeep.dubbo.rpc.util;


public class ProtocolUtils {
    public static final String DOT = ".";
    public static final String COLON = ":";

    public static String genServiceKey(String serviceName, String version, String group) {
        StringBuilder key = new StringBuilder();
        key.append(serviceName).append(COLON)
                .append(version).append(COLON)
                .append(group);
        return key.toString();
    }
}
