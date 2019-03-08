package com.zhb.forever.framework.design.pattern;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月8日上午8:59:23
*/

public class TestMain {
    
    public static void main(String[] args) {
        System.out.println(Singleton.getTest());
        System.out.println(Singleton.getInstance().getTest());
    }


}


