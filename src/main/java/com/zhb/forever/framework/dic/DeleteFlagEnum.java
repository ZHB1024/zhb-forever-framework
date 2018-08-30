package com.zhb.forever.framework.dic;

import java.util.ArrayList;
import java.util.List;

import com.zhb.forever.framework.vo.KeyValueVO;

public enum DeleteFlagEnum {
    
    UDEL("正常",0),DEL("已删除",1);
    
    private String name;
    private int index;
    
    private DeleteFlagEnum(String name,int index){
        this.name = name;
        this.index = index;
    }
    
    public static String getName(int index){
        for (DeleteFlagEnum deleteFlagEnum : DeleteFlagEnum.values()) {
            if (deleteFlagEnum.getIndex() == index) {
                return deleteFlagEnum.getName();
            }
        }
        return "未定义";
    }

    public static List<KeyValueVO> getAll(){
        List<KeyValueVO> vos = new ArrayList<KeyValueVO>();
        for (DeleteFlagEnum deleteFlagEnum: DeleteFlagEnum.values()) {
            KeyValueVO vo = new KeyValueVO();
            vo.setKey(deleteFlagEnum.getName());
            vo.setValue(deleteFlagEnum.getIndex()+"");
            vos.add(vo);
        }
        return vos;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
