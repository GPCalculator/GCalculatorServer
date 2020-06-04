package com.mccspace.hs.tools;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Email��
 * Git to�� http://hs.mccspace.com:3000/Qing_ning/CheckerServer5.0/
 *
 * @TIME 2020/5/26 10:36
 * @AUTHOR ��˶~
 */

public class Email {

    public static void sendEmail(String user, String title, String massage){

        try {

            Message message = new MimeMessage(Parameter.session);

            message.setFrom(new InternetAddress("1750359613@qq.com"));

            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(user)});

            message.setSubject(title);

            message.setText("������ϢΪ�Զ����ͣ��粻Ϊ��������������ԣ�\n\t"+massage);

            Transport transport = Parameter.session.getTransport();

            transport.connect(Parameter.par.getProperty("mail.admin.user"), Parameter.par.getProperty("mail.admin.password"));

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
