package org.limingnihao.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by 唐溶波 on 16/3/4.
 */
public class SendMailUtil {

    /**
     * 发送邮件方法
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param receiveUser 收件人邮箱
     * @throws Exception
     */
    public static void sendEmail(String host, String addresser, String password,String internetAddress,String subject, String content, String receiveUser) throws Exception{
        Properties prop = new Properties();
        prop.setProperty("mail.host", host);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.port","25");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(false);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        ts.connect(host,addresser, password);
        //4、创建邮件
        Message message = createSimpleMail(session,subject,content,internetAddress,receiveUser);
        //5、发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }


    public static MimeMessage createSimpleMail(Session session,String subject, String content,String internetAddress, String receiveUser) throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress(internetAddress));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveUser));
        //邮件的标题
        message.setSubject(subject);
        //邮件的文本内容
        message.setContent(content, "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }


    public static void main(String[] args) throws Exception{

    }

}
