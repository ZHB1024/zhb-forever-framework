package com.zhb.forever.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class JsoupUtil {
    
private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);
    
    public static String IMAGE_BASE_SAVE_PATH ;
    
    static {
        String property = System.getenv("propertyPath");
        if (StringUtil.isNotBlank(property)) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(property);
                Properties properties = new Properties();
                properties.load(fis);
                IMAGE_BASE_SAVE_PATH = properties.getProperty("sys.spider.download.path");
            }catch(Exception e) {
                IMAGE_BASE_SAVE_PATH = "E:\\jsoup-image";
                e.printStackTrace();
                logger.info("JsoupUtil load property fail ......");
            }
        }else {
            IMAGE_BASE_SAVE_PATH = "E:\\jsoup-image";
            logger.info("环境变量未配置propertyPath");
        }
        
    }
    
    public static String getBaseSavePath() {
        return IMAGE_BASE_SAVE_PATH;
    }

    public static Document getDocumentByUrl(String url) {
        if (StringUtil.isBlank(url)) {
            return null;
        }
        Document document = null;
        try {
            
            Connection con = Jsoup.connect(url);
            if (null != con) {
                document = con.get();
            }
        } catch (IOException e) {
            logger.info("init document fail...........");
            e.printStackTrace();
            return null;
        }
        return document;
    }
    
    //从document中获取class为classTarget的内容
    public static Elements getElementsByDocumentClass(Document document,String classTarget) {
        if (null == document || StringUtil.isBlank(classTarget)) {
            return null;
        }
        return document.getElementsByClass(classTarget);
    }
    
    //从document中获取id的内容
    public static Element getElementsByDocumentId(Document document,String id) {
        if (null == document || StringUtil.isBlank(id)) {
            return null;
        }
        return document.getElementById(id);
    }
    
    //从element中按标签获取内容
    public static Elements getElementsBySelect(Element element,String select) {
        if (null == element || StringUtil.isBlank(select)) {
            return null;
        }
        return element.select(select);
    }
    
    //从element中按标签获取内容
    public static Elements getElementsByTag(Element element,String tag) {
        if (null == element || StringUtil.isBlank(tag)) {
            return null;
        }
        return element.getElementsByTag(tag);
    }
    
    //从element中按标签获取内容
    public static String getElementsByAttr(Element element,String attr) {
        if (null == element || StringUtil.isBlank(attr)) {
            return null;
        }
        return element.attr(attr);
    }
    
    public static String getUrl() {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.download.picture.url");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
        return null;
    }
    
    public static String getTotalPage() {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.download.picture.total.page");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
        return null;
    }
    public static String getTotalThread() {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.download.picture.total.thread");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
        return null;
    }
    
    public static String getPersonalizedSavePath() {
        String propertyPath = System.getenv("propertyPath");
        if (null == propertyPath) {
            logger.info("环境变量未配置propertyPath");
        }else{
            FileInputStream in = null;
            try {
                in = new FileInputStream(propertyPath);
                Properties properties = new Properties();
                properties.load(in);
                return properties.getProperty("sys.url.download.save.personalized.path");
            } catch (IOException e) {
                e.printStackTrace();
                logger.info("JsoupUtil load property fail.....");
            }
        }
        return null;
    }

}
