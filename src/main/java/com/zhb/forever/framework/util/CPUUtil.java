package com.zhb.forever.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月31日上午9:08:43
*/

public class CPUUtil {
    
    private static Logger logger = LoggerFactory.getLogger(CPUUtil.class);

    public static final int NCPU = Runtime.getRuntime().availableProcessors();
    
    
    public static void main(String[] args) {
        
        System.out.println(NCPU);
        
    }

}


