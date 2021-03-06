package com.zhb.forever.framework.ftp;

import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: FTPClient
 * @description: 
 * @author: 张会彬
 * @Date: 2019年6月29日 下午5:58:03
 */

public class FtpClient {

    private static Logger logger = LoggerFactory.getLogger(FtpClient.class);

    private FTPClient ftpClient = new FTPClient();
    
    private ClientPOJO clientPOJO = ClientPOJO.getClientPOJOInstance();
    
    public boolean loginFtp() throws SocketException, IOException {
        ftpClient.connect(clientPOJO.getFtpServerIp(), clientPOJO.getFtpServerPort());
        ftpClient.login(clientPOJO.getFtpUserName(), clientPOJO.getFtpPassword());
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        int reply = ftpClient.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftpClient.disconnect();
            return false;
        }
        return true;
    }
    
    public void closeFtp() {
        if (null != ftpClient && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
}
