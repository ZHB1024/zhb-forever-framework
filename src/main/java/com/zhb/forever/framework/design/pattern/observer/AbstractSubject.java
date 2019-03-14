package com.zhb.forever.framework.design.pattern.observer;

import java.util.ArrayList;
import java.util.List;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月14日下午6:13:50
*/

//抽象主题，包含订阅者列表
public abstract class AbstractSubject implements Subject {
    
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void del(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(v ->{
            v.update();
        });
    }

}


