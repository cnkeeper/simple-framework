package com.github.cnkeep;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/16
 * @version:
 **/
public class Test {
    /**
     * N*N的数组旋转90度
     * 行列转换
     */


    public void spin(int[][] arr) {
        int row = arr.length;
        int col = arr[0].length;

        /**
         * [0,1]->[1,0]
         * [1,2,3,4]
         * [11,12,13,14]
         * [21,22,23,24]
         * [31,32,33,34]
         * 快看卡片
         */
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; i++) {
                int tmp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = tmp;
            }
        }
    }
}
