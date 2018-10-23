package com.zhb.forever.framework.proto.exception;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月23日下午3:56:00
*/

public class ProtoCallException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3287407923657282522L;
    
    private int errorCode;

    public ProtoCallException() {
    }

    public ProtoCallException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProtoCallException(String message) {
        super(message);
    }

    public ProtoCallException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}


