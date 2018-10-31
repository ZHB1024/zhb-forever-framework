package com.zhb.forever.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月31日上午9:28:59
*/

public class IOUtil {

    public static void closeQuietly(InputStream input) {
        try {
            if (input != null)
                input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null)
                output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


