package com.zhb.forever.framework.design.pattern.observer;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:06:33
*/

public class Observer02 implements Observer {

    @Override
    public void update() {
        System.out.println("oberver02 收到了通知");
    }

}


