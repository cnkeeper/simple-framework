package com.github.time69.simple_springmvc.util;


/**
 * 描述~
 *
 * @author <a href="1348555156@qq.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public final class StringUtils {
    private static final char space = ' ';

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNotBlank(CharSequence sequence) {
        if (null == sequence || sequence.length() < 1)
            return false;

        int len = sequence.length();
        for (int index = 0; index < len; index++) {
            if(sequence.charAt(index)==space)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isNotBlank("  "));
    }
}
