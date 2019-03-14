package com.zhb.forever.framework.design.pattern.observer;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:07:22
*/

//主题 包含增加、删除、提醒观察者的接口
public interface Subject {
    
    // 增加观察者
    void add(Observer observer);
    
    //删除观察者  
    void del(Observer observer);
    
    //通知所有的观察者
    void notifyObservers();
   
    //自身的操作
    void operation();
    
}


