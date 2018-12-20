package com.zhb.forever.framework.algorithm.sort;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午4:54:40
*
*归并排序
*
*/

public class MergeSort {

    public static void main(String[] args) {
        System.out.println("归并排序-----------");
        
        int[] values = GenerateData.generateData(11);
        for (int i : values) {
            System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        mergeSort(values,0,values.length-1);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    // 归并排序
    public static int[] mergeSort(int[] a, int low, int high) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(a, low, mid);
            mergeSort(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
        }
        return a;
    }
    
    public static void merge(int[] arrays, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 把较小的放入临时数组中
        while (i <= mid && j <= high) {
            if (arrays[i] < arrays[j]) {
                temp[k++] = arrays[i++];
            } else {
                temp[k++] = arrays[j++];
            }
        }

        // 如果左边还剩余，则把左边剩余的放入临时数组中
        while (i <= mid) {
            temp[k++] = arrays[i++];
        }

        // 如果右边还剩余，则把右边剩余的放入临时数组中
        while (j <= high) {
            temp[k++] = arrays[j++];
        }

        // 把排好序的临时数组，整理到目标数组中
        for (int t = 0; t < temp.length; t++) {
            arrays[t + low] = temp[t];
        }
    }

}


