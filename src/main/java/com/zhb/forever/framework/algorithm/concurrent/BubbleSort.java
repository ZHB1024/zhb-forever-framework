package com.zhb.forever.framework.algorithm.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午2:17:44
*
*并行 奇偶交换排序
*
*/

public class BubbleSort {
    
    private static Logger logger = LoggerFactory.getLogger(BubbleSort.class);
    
    private static int[] arr;
    private static int exchFlag = 1;
    
    public static void main(String[] args) {
        System.out.println("并行 奇偶交换排序-----------");
        
        arr = GenerateData.generateData(100000);
        for (int i : arr) {
            //System.out.print(i + " ,");
        }
        
        long start = System.currentTimeMillis();
        oddEvenSort();
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : arr) {
            //System.out.print(i + " ,");
        }
        System.out.println("");
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    //并行化 奇偶交换排序
    public static void oddEvenSort() {
        int length = arr.length;
        int start = 0;
        ExecutorService pool = Executors.newCachedThreadPool();
        while(getExchFlag() == 1 || start == 1) {
            setExchFlag(0);
            //偶数的数组长度，当start为1时，只有len/2-1个线程
            CountDownLatch countDownLatch = new CountDownLatch(length/2-(length%2==0?start:0));
            for(int i = start;i < length-1;i+=2) {
                pool.execute(new OddEvenSortTask(i, countDownLatch));//数据长度比较大时，会创建太多的线程，效率低下
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (start == 0) {
                start = 1;
            }else {
                start = 0;
            }
        }
    }
    
    
    
    private static class OddEvenSortTask implements Runnable{
        int i;
        CountDownLatch countDownLatch;
        
        public OddEvenSortTask(int i,CountDownLatch countDownLatch) {
            this.i = i;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i+1]) {
                swap(arr, i, i+1);
                setExchFlag(1);
            }
            countDownLatch.countDown();
        }
        
    }
    
    private static synchronized void setExchFlag(int flag) {
        exchFlag = flag;
    }
    
    private static synchronized int getExchFlag() {
        return exchFlag;
    }
    
    private static void swap(int[] values, int left, int right) {
        int temp = values[left];
        values[left] = values[right];
        values[right] = temp;
    }

}


