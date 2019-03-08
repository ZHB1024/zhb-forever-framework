package com.zhb.forever.framework.design.pattern.factory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月8日上午9:40:09
*/

public class TestMain {
    
    public static void main(String[] args) {
        Factory provider = new SendMailFactory();
        Sender sender = provider.produce();
        sender.send();
    }

}


