package com.github.cnkeep.simple_springmvc.util;


/**
 * 描述~
 *
 * @author <a href="zhangleili924@gmail.com">LeiLi.Zhang</a>
 * @version 0.0.0
 * @date 2018/8/23
 */
public final class StringUtils {
    private static final char SPACE = ' ';

    private StringUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNotBlank(CharSequence sequence) {
        if (null == sequence || sequence.length() < 1) {
            return false;
        }

        int len = sequence.length();
        for (int index = 0; index < len; index++) {
            if(sequence.charAt(index)== SPACE) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.isNotBlank("  "));
    }
}
