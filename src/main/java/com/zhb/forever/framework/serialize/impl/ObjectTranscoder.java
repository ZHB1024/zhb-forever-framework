package com.zhb.forever.framework.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.zhb.forever.framework.serialize.SerializeTranscoder;

public class ObjectTranscoder<M extends Serializable> extends SerializeTranscoder {

    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(Object value) {
        if (value == null) {
            return null;
        }
        byte[] result = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            M m = (M) value;
            os.writeObject(m);
            os.close();
            bos.close();
            result = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(os);
            close(bos);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deserialize(byte[] in){
        M result = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                result = (M) is.readObject();
                is.close();
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(is);
            close(bis);
        }
        return result;
    }

}
