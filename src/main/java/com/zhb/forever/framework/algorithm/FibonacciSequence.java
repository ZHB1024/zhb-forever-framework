package com.zhb.forever.framework.algorithm;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午5:47:12
*
*斐波那契数列
*
*/

public class FibonacciSequence {
    
    public static void main(String[] args) {
        System.out.println("斐波那契数列-----------");
        
        long start = System.currentTimeMillis();
        int value = getFibo(9);
        long end = System.currentTimeMillis();
        
        System.out.println("值为：" + value);
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }

    // 斐波那契数
    public static int getFibo(int i) {
        if (i < 1) {
            return 0;
        }
        if (i == 1 || i == 2) {
            return 1;
        } else {
            return getFibo(i - 1) + getFibo(i - 2);
        }

    }

}


