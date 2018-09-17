package com.zhb.forever.framework.vo;

public class MailVO {
    
    public MailVO(String toMail,String title,String content,String userName,String password) {
        this.toMail = toMail;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.password = password;
    }

    private String toMail;
    private String title;
    private String content;
    
    private String userName;
    private String password;
    
    public String getToMail() {
        return toMail;
    }
    public void setToMail(String toMail) {
        this.toMail = toMail;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
