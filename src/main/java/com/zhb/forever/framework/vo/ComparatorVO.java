package com.zhb.forever.framework.vo;

import java.util.List;

public class ComparatorVO {
    
    public ComparatorVO(Integer order) {
        this.order = order;
    }
    
    private String id;
    private Integer order;
    private String name;
    private String path;
    private String iconName;
    private List<ComparatorVO> childs; 
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getOrder() {
        return order;
    }
    public void setOrder(Integer order) {
        this.order = order;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getIconName() {
        return iconName;
    }
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
    public List<ComparatorVO> getChilds() {
        return childs;
    }
    public void setChilds(List<ComparatorVO> childs) {
        this.childs = childs;
    }
    
}
