package com.zhb.forever.framework.dic;

import java.util.ArrayList;
import java.util.List;

import com.zhb.forever.framework.vo.KeyValueVO;

/**

*@author   zhanghb

*date 2018年10月21日下午3:55:35

*/

public enum LikeDgreeEnum {
    
    UN_LIKE("不喜欢",1),LIKE("喜欢",2),VERY_LIKE("非常喜欢",3);
    
    private String name;
    private Integer index;
    
    private LikeDgreeEnum(String name,Integer index) {
        this.name = name;
        this.index = index;
    }
    
    public static String getName(int index){
        for (LikeDgreeEnum likeDegreeEnum : LikeDgreeEnum.values()) {
            if (likeDegreeEnum.getIndex() == index) {
                return likeDegreeEnum.getName();
            }
        }
        return "未定义";
    }

    public static List<KeyValueVO> getAll(){
        List<KeyValueVO> vos = new ArrayList<KeyValueVO>();
        for (LikeDgreeEnum likeDegreeEnum: LikeDgreeEnum.values()) {
            KeyValueVO vo = new KeyValueVO();
            vo.setKey(likeDegreeEnum.getName());
            vo.setValue(likeDegreeEnum.getIndex()+"");
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


