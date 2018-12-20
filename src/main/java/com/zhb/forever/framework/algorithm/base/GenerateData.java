package com.zhb.forever.framework.algorithm.base;

import java.util.Random;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午1:52:22
*/

public class GenerateData {

    public static int[] generateData(int number) {
        int[] nums = new int[number];
        Random random = new Random();
        for(int i=0;i < number;i++) {
            nums[i] = random.nextInt(number*100);
        }
        return nums;
    }
    
    public static Integer[] generateInteger(int number) {
        Integer[] nums = new Integer[number];
        Random random = new Random();
        for(int i=0;i < number;i++) {
            nums[i] = random.nextInt(number*100);
        }
        return nums;
    }
    
    public static Integer[] generateIncreaseData(int number) {
        Integer[] nums = new Integer[number];
        for(int i=0;i < number;i++) {
            nums[i] = number*i;
        }
        return nums;
    }
    
    public static void swap(int[] values, int left, int right) {
        int temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }

}


