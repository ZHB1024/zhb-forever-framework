package com.zhb.forever.framework.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhb.forever.framework.serialize.SerializeTranscoder;

public class ListTranscoder<M extends Serializable> extends SerializeTranscoder {
    
    private Logger Logger = LoggerFactory.getLogger(ListTranscoder.class);
    
    @SuppressWarnings("unchecked")
    @Override
    public byte[] serialize(Object value) {
        if (value == null) {
            return null;
        }
        List<M> values = (List<M>) value;
        byte[] results = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;

        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            for (M m : values) {
                os.writeObject(m);
            }
            results = bos.toByteArray();
            os.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(os);
            close(bos);
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<M> deserialize(byte[] in) {
        List<M> list = new ArrayList<>();
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                while (true) {
                    M m = (M) is.readObject();
                    if (m == null) {
                        break;
                    }
                    list.add(m);
                }
            }
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            
        } finally {
            close(is);
            close(bis);
        }
        return list;
    }

}
