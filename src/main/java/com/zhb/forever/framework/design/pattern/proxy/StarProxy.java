package com.zhb.forever.framework.design.pattern.proxy;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2019年3月12日下午5:35:07
*/

public class StarProxy implements Star {
    
    private Star realStar;

    public StarProxy(Star star) {
        this.realStar = star;
    }

    @Override
    public void sing() {
        System.out.println("准备演唱会的前期工作...");
        realStar.sing();//歌星唱歌
        System.out.println("演唱会结束，处理后续工作...");
    }

}


