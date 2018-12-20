package com.zhb.forever.framework.algorithm.search;

import com.zhb.forever.framework.algorithm.base.GenerateData;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月20日下午5:01:05
*
*二分查找、折半查找
*
*/

public class BinarySearch {

    public static void main(String[] args) {
        System.out.println("二分查找、折半查找-----------");
        
        Integer[] values = GenerateData.generateIncreaseData(11);
        for (int i : values) {
            System.out.print(i + " ,");
        }
        Integer target = values[6];
        long start = System.currentTimeMillis();
        //int index = binarySearch(values,target,0,values.length-1);
        //int index = binarySearchByComparable(values,target,0,values.length);
        int index = binarySearchByRecursion(values,target,0,values.length-1);
        long end = System.currentTimeMillis();
        
        System.out.println("");
        for (int i : values) {
            System.out.print(i + " ,");
        }
        System.out.println("");
        
        if (index >=0 ) {
            System.out.println("查找目标" + target + "在数组的下标为：" + index);
        }else {
            System.out.println("数据集中不包含目标"+ target);
        }
        System.out.println("共耗时：" + (end-start)/1000 + " s");
    }
    
    /**
     * 二分查找、折半查找
     * @param array 目标有序序列，从小到大排列
     * @param target  待查找的目标对象
     * @param fromIndex  数组的开始查找下标位置
     * @param toIndex    数组的结束查找下标位置
     * @return 数组的位置
     */
    private static int binarySearch(Integer[] array, int target, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            return -(fromIndex+1);
        }

        //int mid = fromIndex + (target-array[fromIndex])/(array[toIndex]-array[fromIndex])*(toIndex-fromIndex);

        while(toIndex >= fromIndex){
            int mid = (fromIndex + toIndex) / 2;
            if (array[mid] == target) {
                return mid;
            }else if (target < array[mid]) {
                toIndex = mid - 1;
            } else {
                fromIndex = mid + 1;
            }
        }
        return -(fromIndex+1);
    }
    
    /**
     * 二分查找、折半查找
     * @param array 目标有序序列，从小到大排列
     * @param key  待查找的目标对象
     * @param fromIndex  数组的开始查找下标位置
     * @param arrayLength    array.length
     * @return 数组的位置
     */
    public static int binarySearchByComparable(Object[] array, Object key, int fromIndex, int arrayLength) {
        int low = fromIndex;
        int high = arrayLength - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("rawtypes")
            Comparable midVal = (Comparable) array[mid];
            @SuppressWarnings("unchecked")
            int cmp = midVal.compareTo(key);

            if (cmp < 0)
                low = mid + 1;
            else if (cmp > 0)
                high = mid - 1;
            else
                return mid; // key found
        }
        return -(low + 1); // key not found.
    }
    
    /**
     * 二分查找、折半查找
     * @param array 目标有序序列，从小到大排列
     * @param target  待查找的目标对象
     * @param low  下标
     * @param high 上标
     * @return 数组的位置
     */
    public static int binarySearchByRecursion(Integer[] array, int target, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (array[mid] == target) {
            return mid;
        } else if (target < array[mid]) {
            return binarySearchByRecursion(array, target, low, mid - 1);
        } else {
            return binarySearchByRecursion(array, target, mid + 1, high);
        }
    }

}


