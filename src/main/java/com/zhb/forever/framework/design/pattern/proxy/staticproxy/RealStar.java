package com.zhb.forever.framework.design.pattern.proxy.staticproxy;

import com.zhb.forever.framework.design.pattern.proxy.Star;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年12月21日下午2:31:48
*/

public class RealStar implements Star {

    public RealStar() {
    }

    @Override
    public void sing() {
        System.out.println(" I am star,I am singing...");
        
    }

}


