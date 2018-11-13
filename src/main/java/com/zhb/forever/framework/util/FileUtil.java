package com.zhb.forever.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.sanselan.util.IOUtils;

public class FileUtil {
    
    public static final Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();
    public static final Map<String, String> EXCEL_TYPE_MAP = new HashMap<String, String>();
    private static final byte[] DBF_HEADER = { 3, 48 };

    public static int blockSize = 8192;
    public static int bufferSize = blockSize * 10;

    static {
        getAllFileType();
    }

    private static void getAllFileType() {
        FILE_TYPE_MAP.put("ffd8ff", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp");
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp");
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp");

        FILE_TYPE_MAP.put("504b0304", "zip");

        EXCEL_TYPE_MAP.put("d0cf11e0a1b11ae10000", "xls");
        EXCEL_TYPE_MAP.put("504b0304140006000800", "xlsx");
        EXCEL_TYPE_MAP.put("d0cf11e0a1b11ae10000", "xls");
        EXCEL_TYPE_MAP.put("504b03040a0000000000", "xlsx");
    }
    
       /**
        * *过滤目录下的文件
        * @param dirPath 想要获取文件的目录
        * @return 返回文件list
        */
       public static List<File> getFileList(String dirPath) {
           File[] files = new File(dirPath).listFiles();
           List<File> fileList = new ArrayList<File>();
           for (File file : files) {
               if (isTxtFile(file.getName())) {
                   fileList.add(file);
               }
           }
           return fileList;
       }
       
       /**
        * *判断是否为目标文件，目前支持txt xls doc格式
        * @param fileName 文件名称
        * @return 如果是文件类型满足过滤条件，返回true；否则返回false
        */
       public static boolean isTxtFile(String fileName) {
           if (fileName.lastIndexOf(".txt") > 0) {
               return true;
           }else if (fileName.lastIndexOf(".xls") > 0) {
               return true;
           }else if (fileName.lastIndexOf(".doc") > 0) {
               return true;
           }
           return false;
       }
       
       /**
        * *删除文件目录下的所有文件
        * @param file 要删除的文件目录
        * @return 如果成功，返回true.
        */
       public static boolean deleteDir(File file){
           if(file.isDirectory()){
               File[] files = file.listFiles();
               for(int i = 0; i < files.length; i++){
                   deleteDir(files[i]);
               }
           }
           file.delete();
           return true;
       }

    public static void copyTo(File src, File dest) {
        try {
            FileInputStream fis = new FileInputStream(src);
            copyTo(fis, dest);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("文件不存在,file:%s", new Object[] { src.getAbsolutePath() }), e);
        }
    }

    public static void copyTo(InputStream is, File dest) {
        BufferedInputStream bis = new BufferedInputStream(is, bufferSize);
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(dest);
            bos = new BufferedOutputStream(fos, bufferSize);
            int readBytes = 0;
            byte[] block = new byte[blockSize];
            while ((readBytes = bis.read(block)) != -1) {
                bos.write(block, 0, readBytes);
            }
            bos.flush();
            fos.flush();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (null != bos){
                try{
                    bos.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != fos){
                try{
                    fos.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != bis){
                try{
                    bis.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
            if (null != is){
                try{
                    is.close();
                }catch(IOException ex){
                    ex.printStackTrace();
                }

            }
        }
    }


    /**
     ** 获取文件类型
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static String getFileType(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] bytes = IOUtils.getFileBytes(file);
        return getFileType(bytes);
    }

    /**
     * *获取文件类型
     * @param bytes 文件字节数组
     * @return 文件类型
     */
    public static String getFileType(byte[] bytes) {
        String res = null;
        FileInputStream is = null;
        try {
            String fileCode = bytesToHexString(bytes);

            Iterator keyIter = FILE_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                if ((key.toLowerCase().startsWith(fileCode.toLowerCase()))
                    || (fileCode.toLowerCase().startsWith(key.toLowerCase()))) {
                    res = (String) FILE_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((src == null) || (src.length <= 0)) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static boolean isExcel(String filePath) {
        boolean flag = true;
        String res = null;
        FileInputStream is = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            String fileCode = bytesToHexString(b);

            Iterator keyIter = EXCEL_TYPE_MAP.keySet().iterator();
            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                if ((key.toLowerCase().startsWith(fileCode.toLowerCase()))
                    || (fileCode.toLowerCase().startsWith(key.toLowerCase()))) {
                    res = (String) EXCEL_TYPE_MAP.get(key);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if ((!(StringUtil.equals(res, "xls"))) && (!(StringUtil.equals(res, "xlsx")))) {
            flag = false;
        }
        return flag;
    }

    public static boolean isDBF(InputStream is) {
        boolean res = false;
        try {
            if (is != null) {
                byte[] b = new byte[1];
                is.read(b, 0, b.length);
                res = (b[0] == DBF_HEADER[0]) || (b[0] == DBF_HEADER[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return res;
    }
    
    /**
     * *根据文件路径读取byte[] 数组
     */
    public static byte[] readFilePathAsBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            return FileUtils.readFileToByteArray(file);
        }
    }
    
    /**
     * *根据文件路径读取byte[] 数组
     */
    public static byte[] readFileAsBytes(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        } else {
            return FileUtils.readFileToByteArray(file);
        }
    }

    /**
     * inputstream to bytes[]
     */
    public static byte[] readInputStreamAsBytes(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(is);
            short bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len1;
            while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len1);
            }
            byte[] var7 = bos.toByteArray();
            return var7;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }

            bos.close();
        }

    }
    
    
    /**
     * inputstream to file
     */
    public static void copyInputStreamToFile(InputStream is, File file) throws IOException {
        FileUtils.copyInputStreamToFile(is, file);
    }
    
    /**
     * *时间 + “-” + 4位随机数
     * @return
     */
    public static String randomName() {
        StringBuilder sb = new StringBuilder();
        sb.append(DateTimeUtil.getDateTime(Calendar.getInstance(), "yyyyMMddHHmmss"));
        sb.append("-" + RandomUtil.getRandomNumbers(4));
        return sb.toString();
    }
    
    /**
     * *保留小数点后几位数字
     * @param src
     * @param scale 位数
     * @return
     */
    public static Double getFileSizeKB(Double fileSize, int scale) {
        //设置位数  
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
        int   roundingMode  =  4;
        Double src = fileSize/1024;
        BigDecimal   bd  =   new  BigDecimal(src);  
        bd   =  bd.setScale(scale,roundingMode);  
        return bd.doubleValue();
    }
    
    /**
     * *保留小数点后几位数字
     * @param src
     * @param scale 位数
     * @return
     */
    public static Double getFileSizeMB(Double fileSize, int scale) {
        //设置位数  
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
        int   roundingMode  =  4;
        Double src = fileSize/1024/1024;
        BigDecimal   bd  =   new  BigDecimal(src);  
        bd   =  bd.setScale(scale,roundingMode);  
        return bd.doubleValue();
    }
    
    /**
     * *保留小数点后几位数字
     * @param src
     * @param scale 位数
     * @return
     */
    public static Double getFileSizeGB(Double fileSize, int scale) {
        //设置位数  
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
        int   roundingMode  =  4;
        Double src = fileSize/1024/1024/1024;
        BigDecimal   bd  =   new  BigDecimal(src);  
        bd   =  bd.setScale(scale,roundingMode);  
        return bd.doubleValue();
    }
    
    /** 
     * *写文件 
     *  
     * @param content 
     * @param path 
     */  
    public static void writeFile(String content, String path) {  
        FileOutputStream fos = null;  
        BufferedWriter bw = null;  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));  
            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null)  
                    bw.close();  
                if (fos != null)  
                    fos.close();  
            } catch (IOException ie) {  
            }  
        }  
    }  
    
    
    /** 
     * *获取txt文件编码方式
     *  
     * @param file 
     * @return
     */ 
    public static String getCharset(File file) throws IOException {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; // 文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; // 文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; // 文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; // 文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }
}
