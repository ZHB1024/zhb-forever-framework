package com.zhb.forever.framework.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.Constants;


public class UploadUtil {
    
    private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);
    
    /**
     * *inputsteam 写入 file 
     * @param  is   file
     */
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
    
    /**
     * *压缩图片
     * @param  is，fileName，文件后缀，fileSize
     */
    public static String uploadThumbmail(InputStream is,String fileName,String suffix,Long fileSize) {
        String uploadThumbnailPath = null;
        String fileThumbnailPath = PropertyUtil.getUploadThumbnailPath();//缩略图路径
        File fileThumbnailUpload = new File(fileThumbnailPath);
        if (!fileThumbnailUpload.exists()) {
            fileThumbnailUpload.mkdirs();
        }
        uploadThumbnailPath = fileThumbnailPath + File.separator + fileName;
        
        File thumbnailFile = new File(uploadThumbnailPath);
        
        try {
            byte[] bytes = ImageUtil.smallImage(is, suffix,fileSize, Constants.SMALL_IMAGE_SIZE);
            InputStream inputStream = new ByteArrayInputStream(bytes);
            inputStream2File(inputStream, thumbnailFile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        return uploadThumbnailPath;
    }

}
