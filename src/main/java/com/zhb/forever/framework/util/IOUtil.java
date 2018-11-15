package com.zhb.forever.framework.util;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月31日上午9:28:59
*/

public class IOUtil {

    public static void closeQuietly(InputStream input) {
        if (input != null)
            IOUtils.closeQuietly(input);
    }

    public static void closeQuietly(OutputStream output) {
        if (output != null)
            IOUtils.closeQuietly(output);
    }

}


