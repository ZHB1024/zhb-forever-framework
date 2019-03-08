package com.zhb.forever.framework.design.pattern.factory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月8日上午9:37:18
*/

public class SmsSender implements Sender {

    @Override
    public void send() {
        System.out.println("send sms...");
    }

}


