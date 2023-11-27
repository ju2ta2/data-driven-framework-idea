package com.nostromo.utilities;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class MonitoringMail
{
    public void sendMail(String mailServer, String from, String[] to, String subject, String messageBody) throws MessagingException {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", mailServer);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(TestConfig.username, TestConfig.password);
            }
        });

        Message message = new MimeMessage(session);

        InternetAddress[] addressTo = new InternetAddress[to.length];
        for (int i = 0; i < to.length; i++) {
            addressTo[i] = new InternetAddress(to[i]);
        }
        message.setRecipients(Message.RecipientType.TO, addressTo);

        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(messageBody, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}