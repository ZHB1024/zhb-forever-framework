package com.zhb.forever.framework.design.pattern;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月18日上午9:10:17
*
*JVM内部的机制能够保证当一个类被加载的时候，这个类的加载过程是线程互斥的。
*这样当我们第一次调用getInstance的时候，JVM能够帮我们保证instance只被创建一次，
*并且会保证把赋值给instance的内存初始化完毕，这样我们就不用担心上面的问题。
*同时该方法也只会在第一次调用的时候使用互斥机制，这样就解决了低性能问题。
*/

public class Singleton {
    
    private static int test = 10;

    private Singleton() {
        System.out.println("singleton is create ");
    }
    
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
    
    //只有第一次调用这个方法时，才会创建内部类的实例，其它情况均不创建
    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
    
    public static int getTest() {
        return test;
    }

}


