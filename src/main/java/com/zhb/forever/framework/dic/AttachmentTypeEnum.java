package com.zhb.forever.framework.dic;

import java.util.ArrayList;
import java.util.List;

import com.zhb.forever.framework.util.StringUtil;
import com.zhb.forever.framework.vo.KeyValueVO;

public enum AttachmentTypeEnum {
    
    UNDEFINED("undefined",0),IMAGE("image",1),EXCEL("excel",2),WORD("word",3),
    PDF("pdf",4),TXT("txt",5),VIDEO("video",6),AUDIO("audio",7),ZIP("zip",8);
    
    private String name;
    private int index;
    
    private AttachmentTypeEnum(String name,int index){
        this.name = name;
        this.index = index;
    }
    
    public static String getName(int index){
        for (AttachmentTypeEnum fileTypeEnum : AttachmentTypeEnum.values()) {
            if (fileTypeEnum.getIndex() == index) {
                return fileTypeEnum.getName();
            }
        }
        return "未定义";
    }
    
    public static AttachmentTypeEnum geTypeEnum(String fileName) {
        if (StringUtil.isBlank(fileName)) {
            return AttachmentTypeEnum.UNDEFINED;
        }
        if (fileName.contains("png") || fileName.contains("jpg") ||fileName.contains("jpeg") ||fileName.contains("gif") ||fileName.contains("jpe")) {
            return AttachmentTypeEnum.IMAGE;
        }
        if (fileName.contains("xls") || fileName.contains("xlsx") ) {
            return AttachmentTypeEnum.EXCEL;
        }
        if (fileName.contains("doc") || fileName.contains("docx") ) {
            return AttachmentTypeEnum.WORD;
        }
        if (fileName.contains("pdf") ) {
            return AttachmentTypeEnum.PDF;
        }
        if (fileName.contains("txt") ) {
            return AttachmentTypeEnum.TXT;
        }
        if (fileName.contains("mp4") || fileName.contains("avi") ||fileName.contains("wmv") ||fileName.contains("rmvb") ||fileName.contains("rm") ||fileName.contains("flv") ||fileName.contains("ogg")) {
            return AttachmentTypeEnum.VIDEO;
        }
        if (fileName.contains("mp3") || fileName.contains("mpeg3") ) {
            return AttachmentTypeEnum.AUDIO;
        }
        if (fileName.contains("zip") ) {
            return AttachmentTypeEnum.ZIP;
        }
        return AttachmentTypeEnum.UNDEFINED;
    }
    
    public static List<KeyValueVO> getAll(){
        List<KeyValueVO> vos = new ArrayList<KeyValueVO>();
        for (AttachmentTypeEnum fileTypeEnum: AttachmentTypeEnum.values()) {
            KeyValueVO vo = new KeyValueVO();
            vo.setKey(fileTypeEnum.getName());
            vo.setValue(fileTypeEnum.getIndex()+"");
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
