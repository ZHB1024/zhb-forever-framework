package com.zhb.forever.framework.proto.support;

import java.lang.reflect.Method;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.zhb.forever.framework.proto.ProtoResult;
import com.zhb.forever.framework.util.StringUtil;

/**
*@author   zhanghb<a href="mailto:zhb20111503@126.com">zhanghb</a>
*@createDate 2018年10月23日下午3:53:45
*/

public abstract class ProtoConverter<T> {

    public Message converFromProto(String className,ProtoResult rs) throws Exception {
        if (StringUtil.isBlank(className)) {
            return null;
        }
        //String callId = UUIDUtil.getRandomUUID();
        Class c = Class.forName(className);
        Method method = c.getMethod("parseFrom", byte[].class);
        if (null == method) {
            return null;
        }
        return (Message)method.invoke(c, rs.getProtoBytes());
    }
    
    public abstract T converFromProto(ProtoResult paramProtoResult) throws Exception;

}


