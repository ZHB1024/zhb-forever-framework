package com.zhb.forever.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtil {
    
    private static final Log log = LogFactory.getLog(PropertyUtil.class);
    
    private static String appName;
    
    private static String dbUrl;
    private static String dbDriverClassName;
    private static String dbUserName;
    private static String dbPassword;
    
    private static String uploadPath ;
    private static String downloadPath ;
    private static String uploadThumbnailPath ;
    
    //mail
    private static String mailHost;
    private static String mailUserName;
    private static String mailPassword;
    private static String mailPort;
    private static String mailProtocol;
    private static String mailSmtpAuth;
    private static String mailSmtpTimeOut;
    
    //水印
    private static String waterPrintWord ;
    
    //spider
    private static String spiderUrl;
    private static String spiderUrlTarget;
    private static Integer spiderTotalPage;
    
    //system_logo_name
    private static String systemLogoName;
    
    //temp path
    private static String tempUploadPath;
    private static String tempDownloadPath;
    
    //ftp
    private static String ftpServerIP;
    private static Integer ftpServerPort;
    private static String ftpUserName;
    private static String ftpPassword;
    
    static{
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            log.info("环境变量未配置PropertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                appName = properties.getProperty("sys.jdbc.datasourse.vue.appname");
                
                dbUrl = properties.getProperty("sys.jdbc.datasourse.vue.url");
                dbDriverClassName = properties.getProperty("sys.jdbc.datasourse.vue.driverClassName");
                dbUserName = properties.getProperty("sys.jdbc.datasourse.vue.username");
                dbPassword = properties.getProperty("sys.jdbc.datasourse.vue.password");
                
                uploadPath = properties.getProperty("sys.upload.path");
                downloadPath = properties.getProperty("sys.download.path");
                uploadThumbnailPath = properties.getProperty("sys.upload.thumbnail.path");
                
                //mail
                mailHost = properties.getProperty("sys.mail.vue.host");
                mailUserName = properties.getProperty("sys.mail.vue.username");
                mailPassword = properties.getProperty("sys.mail.vue.password");
                mailPort = properties.getProperty("sys.mail.vue.port");
                mailProtocol = properties.getProperty("sys.mail.vue.protocol");
                mailSmtpAuth = properties.getProperty("sys.mail.vue.smtp.auth");
                mailSmtpTimeOut = properties.getProperty("sys.mail.vue.smtp.timeout");

                //水印
                waterPrintWord = properties.getProperty("sys.water.print.word");
                
                //spider
                spiderUrl = properties.getProperty("sys.spider.image.url");
                spiderUrlTarget = properties.getProperty("sys.spider.image.url.target");
                spiderTotalPage = Integer.valueOf(properties.getProperty("sys.spider.image.total.page"));
                
                //system_logo_name
                systemLogoName = properties.getProperty("sys.logo.name");
                
                tempUploadPath = properties.getProperty("sys.temp.upload.path");
                tempDownloadPath = properties.getProperty("sys.temp.download.path");
                
                ftpServerIP = properties.getProperty("sys.ftp.server.ip");
                ftpServerPort = Integer.valueOf(properties.getProperty("sys.ftp.server.port"));
                ftpUserName = properties.getProperty("sys.ftp.username");
                ftpPassword = properties.getProperty("sys.ftp.password");
                
            } catch (IOException e) {
            }
        }
            
    }

    public static String getAppName() {
        return appName;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public static String getDbUserName() {
        return dbUserName;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getUploadPath() {
        return uploadPath;
    }
    public static String getUploadThumbnailPath() {
        return uploadThumbnailPath;
    }

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static String getMailHost() {
        return mailHost;
    }

    public static String getMailUserName() {
        return mailUserName;
    }

    public static String getMailPassword() {
        return mailPassword;
    }

    public static String getMailPort() {
        return mailPort;
    }

    public static String getMailProtocol() {
        return mailProtocol;
    }

    public static String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public static String getMailSmtpTimeOut() {
        return mailSmtpTimeOut;
    }

    public static String getWaterPrintWord() {
        return waterPrintWord;
    }

    public static String getSpiderUrl() {
        return spiderUrl;
    }

    public static Integer getSpiderTotalPage() {
        return spiderTotalPage;
    }

    public static String getSpiderUrlTarget() {
        return spiderUrlTarget;
    }

    public static String getSystemLogoName() {
        return systemLogoName;
    }

    public static String getTempUploadPath() {
        return tempUploadPath;
    }

    public static String getTempDownloadPath() {
        return tempDownloadPath;
    }

    public static String getFtpServerIP() {
        return ftpServerIP;
    }

    public static Integer getFtpServerPort() {
        return ftpServerPort;
    }

    public static String getFtpUserName() {
        return ftpUserName;
    }

    public static String getFtpPassword() {
        return ftpPassword;
    }
    
}
