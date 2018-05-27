package webshop.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailUtils {

    public static void sendMail(String to, String code) {
        /**
         * 1.获得一个Session对象
         * 2.创建一个代表邮件的对象Message
         * 3.发送邮件Transport
         */

        // 1,获得连接对象
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");  // 使用的邮件发送协议
        props.setProperty("mail.smtp.host", "smtp.163.com");    // 发送邮件的服务器地址
        props.setProperty("mail.smtp.port", "25");  // 端口号
        props.setProperty("mail.smtp.auth", "true"); // 需要验证
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sexiaomi44@163.com", "sg1700022805");
            }
        });
        // 打印Debug日志
//        session.setDebug(true);

        // 2.创建邮件对象
        Message message = new MimeMessage(session);
        try {
            // 设置发件人:
            message.setFrom(new InternetAddress("sexiaomi44@163.com"));
            // 设置收件人 抄送CC  密送BCC
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            // 设置标题 主题如果乱码的话 qq邮箱接收不到
            message.setSubject(MimeUtility.encodeText("感谢您的注册,本邮件为激活邮件,您不需要回复", MimeUtility.mimeCharset("gb2312"), null));
            // 设置邮件正文
//            message.setContent("<h1>北大软微商城官方激活邮件!点击下面的连接完成激活操作</h1><h3><a href='http://192.168.0.105:8080/webshop/user_active.action?code="+code+"'>http://192.168.0.105:8080/webshop/user_active.action?code="+code+"</a></h3>", "text/html;charset=UTF-8");
            message.setContent("<h1>北大软微商城官方激活邮件!点击下面的连接完成激活操作</h1><h3><a href='http://192.168.102.31:8080/webshop/user_active.action?code="+code+"'>http://192.168.102.31:8080/webshop/user_active.action?code="+code+"</a></h3>", "text/html;charset=UTF-8");
            // 3.发送邮件:
            Transport.send(message);
//            Transport transport = session.getTransport();
//            transport.connect();
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        sendMail("304028999@qq.com","asdadsf");
//    }
}
