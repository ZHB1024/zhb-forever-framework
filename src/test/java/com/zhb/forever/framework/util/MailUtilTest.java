package com.zhb.forever.framework.util;

import org.junit.Test;

import com.zhb.forever.framework.vo.MailVO;

public class MailUtilTest {
    
    //@Test
    public void sendMailTest() {
        MailVO vo = new MailVO("zhanghb@chsi.com.cn","验证码","0000",PropertyUtil.getMailUserName(),PropertyUtil.getMailPassword(),PropertyUtil.getMailHost());
        EmailUtil.sendMail(vo);
    }

}
 