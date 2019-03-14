package com.zhb.forever.framework.design.pattern.observer;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:23:56
*/

public class TestMain {
    
    public static void  main(String[] args) {
        Subject subject = new MySubject();
        subject.add(new Observer01());
        subject.add(new Observer02());
        subject.operation();
    }

}


