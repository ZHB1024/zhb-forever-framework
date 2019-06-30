package com.zhb.forever.framework.ftp;

import com.zhb.forever.framework.util.PropertyUtil;

/** 
 * @ClassName: ClientPOJO
 * @description: 
 * @author: 张会彬
 * @Date: 2019年6月29日 下午5:58:29
 * 
 * 单例
 */

public class ClientPOJO {
    
    private static final String FTP_SERVER_IP = PropertyUtil.getFtpServerIP();
    private static final Integer FTP_SERVER_PORT = PropertyUtil.getFtpServerPort();
    private static final String FTP_USERNAME = PropertyUtil.getFtpUserName();
    private static final String FTP_PASSWORD = PropertyUtil.getFtpPassword();
    
    private ClientPOJO() {
    }
    
    public static ClientPOJO getClientPOJOInstance() {
        return ClientPOJOInstance.CLIENT_POJO;
    }
    
    private static class ClientPOJOInstance{
        private static final ClientPOJO CLIENT_POJO = new ClientPOJO();
    }
    
    public String getFtpServerIp() {
        return FTP_SERVER_IP;
    }
    
    public Integer getFtpServerPort() {
        return FTP_SERVER_PORT;
    }
    
    public String getFtpUserName() {
        return FTP_USERNAME;
    }
    
    public String getFtpPassword() {
        return FTP_PASSWORD;
    }

}
