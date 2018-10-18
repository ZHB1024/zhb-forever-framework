package com.zhb.forever.framework.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.zhb.forever.framework.util.StringUtil;

/**

*@author   zhanghb

*date 2018年10月18日下午3:12:21

*/

public class ExceptionUtil {

    /**
     * *返回错误信息字符串
     * 
     * @param ex
     *            Exception
     * @return 错误信息字符串
     */
    public static String getExceptionMessage(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
    
    /**
     * *如果目标为空则抛出异常
     * @param target
     * @param errorMessage
     */
    public static void throwIfNull(Object target,String errorMessage){
        if(target==null){
            throw new BusinessException(errorMessage);
        }
    }
    
    /**
     * *如果目标为空则抛出异常
     * *本方法空指针安全
     * @param target
     * @param errorMessage
     */
    public static void throwIfEmpty(String target,String errorMessage)
    {
        if(StringUtil.isBlank(target)){
            throw new BusinessException(errorMessage);
        }
    }


}


