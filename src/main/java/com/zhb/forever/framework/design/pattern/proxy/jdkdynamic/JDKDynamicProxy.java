package com.zhb.forever.framework.design.pattern.proxy.jdkdynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月21日下午2:33:28
*
*JDK动态代理
*
*JDK 动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler 来处理。
*但是 JDK 动态代理有个缺憾，或者说特点：JDK 实现动态代理需要实现类通过接口定义业务方法。
*
*/

public class JDKDynamicProxy implements InvocationHandler {
    
    private Object object;

    public JDKDynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("助理安排场地----");
        Object ob = method.invoke(this.object, args);
        System.out.println(ob.toString());
        System.out.println("演唱会结束，助理做后续工作----");
        return ob;
    }
    
    /**
     * 获取被代理接口实例对象
     * @param <T>
     * @return
     */
    public <T> T getProxyInstance() {
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }

}


