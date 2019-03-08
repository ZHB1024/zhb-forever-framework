package com.zhb.forever.framework.design.pattern.factory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月8日上午9:39:35
*/

public class SendSmsFactory implements Factory {

    @Override
    public Sender produce() {
        return new SmsSender();
    }

}


