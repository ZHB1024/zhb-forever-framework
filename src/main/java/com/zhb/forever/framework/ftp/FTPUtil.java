package com.zhb.forever.framework.ftp;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: FTPUtil
 * @description: 
 * @author: 张会彬
 * @Date: 2019年6月29日 下午5:57:45
 */

public class FTPUtil {
    
    private static Logger logger = LoggerFactory.getLogger(FTPUtil.class);
    
    public static List<String> listFiles(FtpClient ftpClient){
        if (null == ftpClient) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        try {
            boolean login = ftpClient.loginFtp();
            if (login) {
                logger.info("login ftp success");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        
        return result;
    }
    
    public static void closeFtp(FtpClient ftpClient) {
        if (null != ftpClient) {
            ftpClient.closeFtp();
        }
    }
    
}
