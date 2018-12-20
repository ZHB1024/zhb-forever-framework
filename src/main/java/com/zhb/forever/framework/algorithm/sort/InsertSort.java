package com.zhb.forever.framework.algorithm.sort;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午4:48:35
*
*插入排序
*
*/

public class InsertSort {

    public static void main(String[] args) {
        System.out.println("插入排序-----------");
        
        int[] values = GenerateData.generateData(10);
        for (int i : values) {
            System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        insertSort(values);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    // 插入排序
    public static void insertSort(int[] arrays) {
        if (null == arrays) {
            return;
        }

        int length = arrays.length;
        int j;
        for (int i = 1; i < length; i++) {
            int temp = arrays[i];
            for (j = i; j > 0 && temp < arrays[j - 1]; j--) {
                arrays[j] = arrays[j - 1];
            }
            arrays[j] = temp;
        }

    }

}


