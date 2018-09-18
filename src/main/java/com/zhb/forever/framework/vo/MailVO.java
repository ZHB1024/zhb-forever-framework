package com.zhb.forever.framework.vo;

public class MailVO {
    
    public MailVO(String toMail,String title,String content,String userName,String password,String host) {
        this.toMail = toMail;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.password = password;
        this.host = host;
    }

    private String toMail;
    private String title;
    private String content;
    
    private String userName;
    private String password;
    private String host;
    
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
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }

}
