package com.zhb.forever.framework.algorithm.sort;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午4:43:10
*
*简单选择排序
*
*/

public class SimpleSelectSort {

    public static void main(String[] args) {
        System.out.println("简单选择排序-----------");
        
        int[] values = GenerateData.generateData(10);
        for (int i : values) {
            System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        selectSort(values);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    // 简单选择排序
    public static void selectSort(int[] arrays) {
        if (null == arrays) {
            return;
        }

        int length = arrays.length;
        int min, j;
        for (int i = 0; i < length; i++) {
            min = i;
            for (j = i + 1; j < length; j++) {
                if (arrays[min] > arrays[j]) {
                    min = j;
                }
            }
            if (min != i) {
                GenerateData.swap(arrays, i, min);
            }
        }

    }
}


