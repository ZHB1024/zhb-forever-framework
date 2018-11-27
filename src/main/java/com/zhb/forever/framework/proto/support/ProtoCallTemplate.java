package com.zhb.forever.framework.proto.support;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.proto.ProtoResult;
import com.zhb.forever.framework.proto.RemoteCallRs;
import com.zhb.forever.framework.proto.exception.ProtoCallException;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年11月27日下午3:17:57
*/

public class ProtoCallTemplate {
    
    protected static Logger log = LoggerFactory.getLogger(ProtoCallTemplate.class);
    
    public static <T> T callProtoMethod2(ProtoConverter<T> convert, Object service, String methodName, Object[] args,
            Class<?>[] param) {
        try {
            RemoteCallRs rcs = callProtoMethod(convert, service, methodName, args, param);
            if (null == rcs) {
                ProtoCallException e = new ProtoCallException("返回对象为空");
                e.setErrorCode(10);
                throw e;
            }
            if ((0 == rcs.getCallResult()) || (9 == rcs.getCallResult())) {
                return (T) rcs.getValue();
            }
            ProtoCallException e = new ProtoCallException(rcs.getErrorMsg());
            e.setErrorCode(rcs.getCallResult());
            throw e;
        } catch (Throwable e) {
            ProtoCallException e2 = new ProtoCallException("远程调用异常", e);
            e2.setErrorCode(11);
            throw e2;
        }
    }

    public static <T> RemoteCallRs<T> callProtoMethod(ProtoConverter<T> convert, Object service, String methodName,
            Object[] args, Class[] param) {
        ProtoResult rs = null;
        RemoteCallRs rcs = new RemoteCallRs();
        try {
            Method method = service.getClass().getMethod(methodName, param);

            Object mrs = method.invoke(service, args);
            rs = (ProtoResult) mrs;
            if (rs.getCallResult() == 0) {
                try {
                    Object value = convert.converFromProto(rs);
                    rcs.setCallResult(rs.getCallResult());
                    rcs.setValue(value);

                    return rcs;
                } catch (Exception e) {
                    StringBuffer errMsg = new StringBuffer();
                    ((StringBuffer) errMsg).append("proto协议转换错误,service[");
                    ((StringBuffer) errMsg).append(service.getClass().getSimpleName());
                    ((StringBuffer) errMsg).append("]method[");
                    ((StringBuffer) errMsg).append(method.getName());
                    ((StringBuffer) errMsg).append("]");
                    log.error(((StringBuffer) errMsg).toString());

                    rcs.setCallResult(8);
                    rcs.setErrorMsg(e.getMessage());

                    return rcs;
                }
            }
                
            if (rs.getCallResult() == 9) {
                rcs.setCallResult(rs.getCallResult());
                rcs.setErrorMsg(rs.getErrorMsg());
                return rcs;
            }
            StringBuffer errMsg = new StringBuffer();
            errMsg.append("调用proto服务失败,service[");
            errMsg.append(service.getClass().getSimpleName());
            errMsg.append("]method[");
            errMsg.append(method.getName());
            errMsg.append("]");
            errMsg.append(rs.getErrorMsg());
            log.error(errMsg.toString());

            rcs.setCallResult(rs.getCallResult());
            rcs.setErrorMsg(rs.getErrorMsg());
            rcs.setValue(null);

            return rcs;
        } catch (IllegalArgumentException e) {
            rcs.setCallResult(1);
            rcs.setErrorMsg(e.getMessage());
            log.error("参数错误", e);
        } catch (IllegalAccessException e) {
            rcs.setCallResult(99);
            rcs.setErrorMsg(e.getMessage());
            log.error("非法访问", e);
        } catch (InvocationTargetException e) {
            rcs.setCallResult(99);
            rcs.setErrorMsg(e.getMessage());
            log.error("访问错误", e);
        } catch (SecurityException e) {
            rcs.setCallResult(99);
            rcs.setErrorMsg(e.getMessage());
            log.error("安全错误", e);
        } catch (NoSuchMethodException e) {
            rcs.setCallResult(99);
            rcs.setErrorMsg(e.getMessage());
            log.error("没有对应的方法", e);
        } catch (Throwable e) {
            rcs.setCallResult(99);
            rcs.setErrorMsg(e.getMessage());
            log.error("未知异常", e);
        } finally {
        }
        return ((RemoteCallRs<T>) rcs);
    }

}


