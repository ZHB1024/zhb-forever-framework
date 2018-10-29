package com.zhb.forever.framework.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月29日下午5:05:01
*/

public class Tess4jUtil {
    
    private static Logger logger = LoggerFactory.getLogger(Tess4jUtil.class);
    
    public static String detectContent(String filePath) {
        /*if (StringUtil.isBlank(filePath)) {
            return null;
        }
        
        File imageFile = new File(filePath);
        
        if (!imageFile.exists()) {
            return null;
        }
        
        ITesseract instance = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());//设置训练库的位置
        instance.setLanguage("eng");//chi_sim ：简体中文， eng    根据需求选择语言库
        
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            
             result =  instance.doOCR(imageFile);
             
            long endTime = System.currentTimeMillis();
            
            logger.info("Time is：" + (endTime - startTime) + " 毫秒");
            logger.info("result:");
            logger.info(result);
            return result;
        } catch (TesseractException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    

}


