package com.github.cnkeep.common.utils;

public class Hex62Util {
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE = CHARS.length();

    /**
     * 将数字转为62进制
     *
     * @param num Long 型数字
     * @return 62进制字符串
     */
    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            num = num / SCALE;
        }
        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        return sb.reverse().toString();
    }

    /**
     * 62进制字符串转为数字
     *
     * @param str 编码后的62进制字符串
     * @return 解码后的 10 进制字符串
     */
    public static long decode(String str) {
        long num = 0;
        int index;
        for (int i = 0; i < str.length(); i++) {
            index = CHARS.indexOf(str.charAt(i));
            num += (long) (index * (Math.pow(SCALE, str.length() - i - 1)));
        }
        return num;
    }
}