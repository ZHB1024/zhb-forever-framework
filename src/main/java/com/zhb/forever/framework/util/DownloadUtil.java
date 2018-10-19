package com.zhb.forever.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadUtil {
    
    private static Logger logger = LoggerFactory.getLogger(DownloadUtil.class);
    
    // 照片缓存30天
    public static final int TIME_OUTS_SECOND = 2592000;
    
    /**
     * *下载前，处理文件名和文件类型
     * @param  fileName  contentType
     */
    public static void processBeforeDownload(HttpServletRequest request, HttpServletResponse response,
            String contentType, String fileName) throws IOException {
        if (StringUtil.isBlank(contentType)) {
            contentType = "APPLICATION/OCTET-STREAM";
        }
        response.setContentType(String.format("%s;charset=utf-8", new Object[] { contentType }));
        response.setHeader("Content-Disposition",
                String.format("attachment;filename=\"%s\"", new Object[] { getCodedFileName(request, fileName) }));
    }
    
    /**
     * *下载附件
     * @param  filePath
     */
    public static void downloadAttachment(HttpServletRequest request, HttpServletResponse response,String filePath) {
        File defaultImage = new File(filePath);
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        try {
            fis = new FileInputStream(defaultImage);
            bi = new BufferedInputStream(fis);
            sos = response.getOutputStream();
            bo = new BufferedOutputStream(sos);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = bi.read(buffer, 0, buffer.length)) != -1) {
                bo.write(buffer, 0, bytesRead);
            }
            bo.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bo) {
                try {
                    bo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bi) {
                try {
                    bi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * *下载图片，加水印
     * @param  filePath  isGif
     */
    public static void downloadAttachmentWithWaterPrint(HttpServletRequest request, HttpServletResponse response,String filePath,boolean isGif) {
        File file = new File(filePath);
        
        FileInputStream fis = null;
        ServletOutputStream sos = null;
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        try {
            fis = new FileInputStream(file);
            sos = response.getOutputStream();
            bi = new BufferedInputStream(fis);
            bo = new BufferedOutputStream(sos);
            if (isGif) {
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = bi.read(buffer, 0, buffer.length)) != -1) {
                    bo.write(buffer, 0, bytesRead);
                }
                bo.flush();
            }else {
                ImageUtil.pressText(bi, bo, 0.3f, 3, 3, new String[] { PropertyUtil.getWaterPrintWord() });
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != bo) {
                try {
                    bo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sos) {
                try {
                    sos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bi) {
                try {
                    bi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * *在浏览器端缓存，设置过期时间
     * @param  fileName  contentType
     */
    public static void processExpiresTime(HttpServletResponse response) {
        long currentTimeMillis = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", currentTimeMillis);
        //在浏览器缓存30天
        response.setDateHeader("Expires", currentTimeMillis + 1000*DownloadUtil.TIME_OUTS_SECOND);
    }
    
    public static String getCodedFileName(HttpServletRequest request, String fileName)
            throws UnsupportedEncodingException {
        String codedfilename = null;
        String agent = request.getHeader("USER-AGENT");
        if (isIEBrowser(agent)) {
            int lastDot = fileName.lastIndexOf(".");
            String prefix = (lastDot != -1) ? fileName.substring(0, lastDot) : fileName;
            String extension = (lastDot != -1) ? fileName.substring(lastDot) : "";
            String name = URLEncoder.encode(fileName, "UTF8");
            if (name.lastIndexOf("%0A") != -1) {
                name = name.substring(0, name.length() - 3);
            }
            int limit = 500 - extension.length();
            if (name.length() > limit) {
                name = URLEncoder.encode(prefix.substring(0, Math.min(prefix.length(), limit / 9)), "UTF-8");
                if (name.lastIndexOf("%0A") != -1) {
                    name = name.substring(0, name.length() - 3);
                }
                name = new StringBuilder().append(name).append(URLEncoder.encode(extension, "UTF8")).toString();
            }
            name = name.replace("+", "%20");
            codedfilename = name;
        } else if ((null != agent) && (-1 != agent.indexOf("Mozilla"))) {
            if (-1 != agent.indexOf("Firefox")) {
                codedfilename = String.format("=?UTF-8?B?%s?=",
                        new Object[] { new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))) });
            } else {
                codedfilename = URLEncoder.encode(fileName, "UTF-8");
                codedfilename = codedfilename.replace("+", "%20");
            }
        } else {
            codedfilename = fileName;
        }
        return codedfilename;
    }
    
    private static boolean isIEBrowser(String agent) {
        boolean result = false;
        if (StringUtil.isNotBlank(agent)) {
            result = (-1 != agent.indexOf("MSIE")) || (-1 != agent.indexOf("Trident"));
        }
        return result;
    }
    
    /**
     * *根据网络地址下载文件
     * 
     * @param urlStr   fileName   savePath
     * */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        if (StringUtil.isBlank(urlStr)) {
            return;
        }
        if (!urlStr.contains("http")) {
            urlStr += "http://";
        }
        URL url = new URL(urlStr);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
                //设置超时间为3秒  
        //conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取字节数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdirs();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
    }  
    
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }    

}
