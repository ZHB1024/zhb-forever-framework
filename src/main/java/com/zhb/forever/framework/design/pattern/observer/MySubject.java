package com.zhb.forever.framework.design.pattern.observer;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:17:06
*/

//我的主题
public class MySubject extends AbstractSubject {

    @Override
    public void operation() {
        System.out.println("my subject is update");
        notifyObservers();//有更改 提醒订阅者
    }

}


