package com.zhb.forever.framework.design.pattern.proxy.cglib;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月21日下午2:48:33
*
*CGLib代理
*
*/

public class CGLibProxy implements MethodInterceptor {
    
    private Object object;

    public CGLibProxy(Object object) {
        this.object = object;
    }

    //给目标对象创建一个代理对象
    public Object getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(object.getClass());
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return en.create();

    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("助理安排场地----");
      //执行目标对象的方法
        Object ob = method.invoke(this.object, args);
        System.out.println(ob.toString());
        System.out.println("演唱会结束，助理做后续工作----");
        return ob;
    }

}


