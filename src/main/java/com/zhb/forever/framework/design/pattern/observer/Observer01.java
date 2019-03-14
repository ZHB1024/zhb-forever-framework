package com.zhb.forever.framework.design.pattern.observer;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:05:45
*/

//观察者01
public class Observer01 implements Observer {

    @Override
    public void update() {
        System.out.println("oberver01 收到了通知");
    }

}


