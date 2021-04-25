package com.github.cnkeep.interview;

import java.util.Scanner;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/18
 * @version:
 **/
public class Main1 {
    private static final String ERROR_MESSAGE = "!error";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String line = in.nextLine();
            String result = uncompress(line);
            System.out.println(result);
        }
    }

    private static String uncompress(String line) {
        if (line == null || line.length() < 1
                || line.length() > 100
                || Character.isDigit(line.charAt(line.length() - 1))) {
            return ERROR_MESSAGE;
        }

        StringBuilder sb = new StringBuilder();
        int len = line.length();
        char pre = '#';
        for (int i = 0; i < len; i++) {
            char ch = line.charAt(i);
            if (Character.isDigit(ch)) {
                StringBuilder countStr = new StringBuilder();
                while (Character.isDigit(line.charAt(i))) {
                    countStr.append(line.charAt(i));
                    i++;
                }

                int count = Integer.parseInt(countStr.toString());
                if (count < 3) {
                    return ERROR_MESSAGE;
                }

                char cur = line.charAt(i);
                if (!isLegal(cur)) {
                    // 非法字符
                    return ERROR_MESSAGE;
                }
                if (i + 1 < len && cur == line.charAt(i + 1)) {
                    //10aa
                    return ERROR_MESSAGE;
                }
                if (pre == cur) {
                    // 12a3a
                    return ERROR_MESSAGE;
                }

                append(sb, count, cur);
                pre = cur;
            } else if (!Character.isLowerCase(ch)) {
                return ERROR_MESSAGE;
            } else {
                sb.append(ch);
                if (i>2 && ch == line.charAt(i - 1) && ch == line.charAt(i - 2)) {
                    return ERROR_MESSAGE;
                }
                pre = ch;
            }
        }

        if (sb.length() > 100) {
            return ERROR_MESSAGE;
        }
        return sb.toString();
    }

    private static void append(StringBuilder sb, int count, char next) {
        for (int k = 0; k < count; k++) {
            sb.append(next);
        }
    }


    private static boolean isLegal(char ch) {
        if (Character.isDigit(ch)) {
            return false;
        } else if (!Character.isLowerCase(ch)) {
            return false;
        } else {
            return true;
        }
    }
}
