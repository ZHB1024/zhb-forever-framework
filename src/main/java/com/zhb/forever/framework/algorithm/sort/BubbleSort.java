package com.zhb.forever.framework.algorithm.sort;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午1:51:24
*
*冒泡排序
*
*/

public class BubbleSort {

    public static void main(String[] args) {
        System.out.println("冒泡排序-----------");
        
        int[] values = GenerateData.generateData(100000);
        for (int i : values) {
            //System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        bubbleSort(values);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            //System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    // 冒泡排序O(n^2)
    public static void bubbleSort(int[] values) {
        if (null == values) {
            return;
        }

        int length = values.length;
        for(int i=length-1;i>0;i--) {
            for(int j=0;j<i;j++) {
                if(values[j]>values[j+1]) {
                    swap(values, j, j+1);
                }
            }
        }
    }
    
    private static void swap(int[] values, int left, int right) {
        int temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }


}


