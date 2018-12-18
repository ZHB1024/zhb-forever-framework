package com.zhb.forever.framework.design.pattern;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月18日上午9:10:17
*/

public class Singleton {
    
    public static int test = 10;

    private Singleton() {
        System.out.println("singleton is create ");
    }
    
    private static class SingletonHolder {
        private static Singleton instance = new Singleton();
    }
    
    public static Singleton getInstance() {
        return SingletonHolder.instance;
    }

}


