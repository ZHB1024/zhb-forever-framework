package com.zhb.forever.framework.algorithm.sort;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午4:52:12
*
*希尔排序
*
*/

public class ShellSort {

    public static void main(String[] args) {
        System.out.println("希尔排序-----------");
        
        int[] values = GenerateData.generateData(10);
        for (int i : values) {
            System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        shellSort(values);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    // 希尔排序
    public static void shellSort(int[] arrays) {
        if (null == arrays) {
            return;
        }

        int j;
        int length = arrays.length;
        for (int gap = length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < length; i++) {
                int temp = arrays[i];
                for (j = i; j >= gap && temp < arrays[j - gap]; j -= gap) {
                    arrays[j] = arrays[j - gap];
                }
                arrays[j] = temp;
            }
        }

    }

}


