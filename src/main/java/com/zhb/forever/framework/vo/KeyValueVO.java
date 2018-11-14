package com.zhb.forever.framework.vo;

import java.io.Serializable;
import java.util.Calendar;

public class KeyValueVO implements Comparable, Serializable {
	
    /**
     * 
     */
    private static final long serialVersionUID = 2897384798977137141L;
    private String id;
	private String key ;
	private String value ;
	private Integer count;
	private Calendar createTime;
	
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    public Calendar getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
    @Override
    public int compareTo(Object object) {
        KeyValueVO o = (KeyValueVO) object;
        return this.key.compareTo(o.getKey());
    }
    
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        KeyValueVO kvp = (KeyValueVO) obj;

        String key1 = kvp.getKey();

        return (this.key.equals(key1));
    }
	
}
