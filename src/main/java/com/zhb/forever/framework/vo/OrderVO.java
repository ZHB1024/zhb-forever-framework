package com.zhb.forever.framework.vo;

public class OrderVO {
    
    private String propertyName;//排序字段
    private boolean ascending;//asc:true;desc:false
    
    public OrderVO(String propertyName,boolean ascending) {
        this.propertyName = propertyName;
        this.ascending = ascending;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
    public boolean isAscending() {
        return ascending;
    }
    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

}
