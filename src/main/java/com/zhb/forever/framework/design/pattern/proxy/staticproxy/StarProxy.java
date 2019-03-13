package com.zhb.forever.framework.design.pattern.proxy.staticproxy;

import com.zhb.forever.framework.design.pattern.proxy.Star;

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
        System.out.println("助理安排场地----");
        realStar.sing();//歌星唱歌
        System.out.println("演唱会结束，助理做后续工作----");
    }

}


