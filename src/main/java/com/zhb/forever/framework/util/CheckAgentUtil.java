package com.zhb.forever.framework.util;

public class CheckAgentUtil {

    public static boolean checkAgentIsMobile(String userAgent) {
        String[] agent = {"Android", "iPhone", "iPod", "Windows Phone", "MQQBrowser"};
        boolean flag = false;
        if (!userAgent.contains("Windows NT") || (userAgent.contains("Windows NT") && userAgent.contains("compatible; MSIE 9.0;"))) {
            // 排除 苹果桌面系统
            if (!userAgent.contains("Windows NT") && !userAgent.contains("Macintosh")) {
                for (String item : agent) {
                    if (userAgent.contains(item)) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

}
