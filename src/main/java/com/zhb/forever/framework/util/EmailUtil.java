package com.zhb.forever.framework.util;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.zhb.forever.framework.vo.MailVO;

public class EmailUtil {

    private static Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static String sendMail(MailVO mailVo) {
        if (null == mailVo) {
            return "信息必须填写！";
        }
        if (StringUtil.isBlank(mailVo.getToMail())) {
            return "邮件地址必须填写！";
        }
        if (StringUtil.isBlank(mailVo.getTitle())) {
            return "邮件标题必须填写！";
        }
        if (StringUtil.isBlank(mailVo.getContent())) {
            return "邮件内容必须填写！";
        }
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.126.com");
        sender.setUsername(mailVo.getUserName());// 发送方的邮箱，例如zhb2011150@126.com
        sender.setPassword(mailVo.getPassword());// 发送方邮箱的密码
        sender.setPort(25);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "smtp");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.timeout", "25000");
        sender.setJavaMailProperties(properties);

        MimeMessage mimeMessage = sender.createMimeMessage();

        // 可以对邮件内容加自定义样式，我这里没有加
        String content_start = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf8\"/></head><body>";
        String content_end = "</body></html>";
        StringBuilder str = new StringBuilder();
        str.append(content_start);
        str.append(mailVo.getContent());
        str.append(content_end);
        String content = str.toString();

        // mimeMessage.setContent(text, "text/html;charset=GB18030");
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true, "GBK");
            helper.setFrom(sender.getUsername());
            helper.setTo(mailVo.getToMail());
            helper.setSubject(mailVo.getTitle());
            helper.setText(content, true);
            sender.send(mimeMessage);
        } catch (MessagingException e1) {
            logger.info("发送邮件时，发送失败");
            logger.info(e1.getMessage());
            e1.printStackTrace();
            return "发送失败！";
        }
        return null;
    }
}
