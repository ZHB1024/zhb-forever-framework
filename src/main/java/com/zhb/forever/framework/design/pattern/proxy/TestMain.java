package com.zhb.forever.framework.design.pattern.proxy;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月21日下午2:52:30
*/

public class TestMain {
    public static void main(String[] args) {
        //静态代理
        Star realStar = new RealStar();
        Star starProxy = new StarProxy(realStar);
        starProxy.sing();
                
        //jdk动态代理
        
        //CGLib代理
        
    }
}


