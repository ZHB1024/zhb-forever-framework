package com.zhb.forever.framework;

public class Constants {

    // 上传文件大小设置
    public static final Long FILE_MAX_SIZE = 20*1024*1024L;//20MB
    public static final String FILE_MAX_SIZE_MB = FILE_MAX_SIZE/1024/1024 + "MB";//20MB
    
    //image 大小设置
    public static Long IMAGE_MAX_SIZE = 5242880L;//5MB
    public static final String IMAGE_MAX_SIZE_MB = IMAGE_MAX_SIZE/1024/1024 + "MB";//5MB
    
    //缩略图大小
    public static final long SMALL_IMAGE_SIZE = 10240;//10KB

}
