package com.mail.springmail.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("163")
public class SpringMail {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String send;
    //普通邮件
    @Test
    public  void text ()throws Exception{
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(send);
        message.setTo(send);
        message.setSubject("邮件测试!");
        message.setText("这是一封使用spring boot技术发送的邮件!");
        javaMailSender.send(message);
    }
    //HTML邮件
    @Test
    public void sendHtml() throws Exception{
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper help=new MimeMessageHelper(message,true);
        help.setTo(send);//发件人
        help.setFrom(send);//收件人
        help.setCc("1530858803@qq.com");//抄送人
        help.setBcc("1513193132@qq.com");//密送人
        help.setSubject("邮件测试");
        StringBuffer sb=new StringBuffer();
        sb.append("<h1>HTML邮件</h1>");
        sb.append("<p>这是一个html邮件</p>");
        help.setText(sb.toString(),true);
        javaMailSender.send(message);
    }
    //带附件的邮箱
    @Test
    public void sendEnclosure()throws Exception{
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper help=new MimeMessageHelper(message,true);
        help.setTo(send);
        help.setFrom(send);
        help.setSubject("带附件的邮件");
        help.setText("这是一封带附件的邮件");
        FileSystemResource system=new FileSystemResource(new File("d://text.txt"));
        help.addAttachment("文件.txt",system);
        javaMailSender.send(message);
    }
    //带静态资源的邮件
    @Test
    public void sendInlineMail()throws  Exception{
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);
        messageHelper.setFrom(send);
        messageHelper.setTo(send);
        messageHelper.setSubject("这是一封带静态资源的邮件");
        messageHelper.setText("<html><body>带静态资源内容的邮件,内容为<img src='cid:picture' /></body></html>");
        FileSystemResource file=new FileSystemResource(new File("c://123.jpg"));
        messageHelper.addInline("picture",file);
        javaMailSender.send(message);
    }

}
