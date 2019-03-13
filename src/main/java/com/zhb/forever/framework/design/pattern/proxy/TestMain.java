package com.zhb.forever.framework.design.pattern.proxy;

import com.zhb.forever.framework.design.pattern.proxy.cglib.CGLibProxy;
import com.zhb.forever.framework.design.pattern.proxy.jdkdynamic.JDKDynamicProxy;
import com.zhb.forever.framework.design.pattern.proxy.staticproxy.RealStar;
import com.zhb.forever.framework.design.pattern.proxy.staticproxy.StarProxy;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月21日下午2:52:30
*/

public class TestMain {
    public static void main(String[] args) {
        //目标对象
        Star realStar = new RealStar();
        
        //静态代理
        Star starProxy = new StarProxy(realStar);
        starProxy.sing();
                
        //jdk动态代理
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(realStar);
        Star jdkProxyInstance = (Star)jdkDynamicProxy.getProxyInstance();
        jdkProxyInstance.sing();
        
        //CGLib代理
        CGLibProxy cgLibProxy = new CGLibProxy(realStar);
        Star cglibProxyStance = (Star)cgLibProxy.getProxyInstance();
        cglibProxyStance.sing();
        
    }
}


