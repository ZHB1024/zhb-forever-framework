package com.zhb.forever.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UploadUtil {
    
    private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);
    
    public static void inputStream2File(InputStream is , File file) {
        OutputStream licOutput = null;
        try {
            licOutput = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                licOutput.write(b, 0, len);
            }
            licOutput.flush();
            
        } catch (IOException e) {
            logger.error("upload exception....");
            e.printStackTrace();
        }finally {
            if (null != licOutput) {
                try {
                    licOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
