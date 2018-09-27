package com.zhb.forever.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class DownloadUtil {
    
    public static void downloadDefault(HttpServletRequest request, HttpServletResponse response,String filePath) {
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
            if (null != sos) {
                try {
                    sos.close();
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
    
    public static void processBeforeDownload(HttpServletRequest request, HttpServletResponse response,
            String contentType, String fileName) throws IOException {
        if (StringUtil.isBlank(contentType)) {
            contentType = "APPLICATION/OCTET-STREAM";
        }
        response.setContentType(String.format("%s;charset=utf-8", new Object[] { contentType }));
        response.setHeader("Content-Disposition",
                String.format("attachment;filename=\"%s\"", new Object[] { getCodedFileName(request, fileName) }));
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

}
