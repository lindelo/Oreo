package imy.oreo.nancy;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

    final static String USERNAME = "imy320nancy@gmail.com";
    final static String PASSWORD = "imy320oreo";

    public void send(String from, String to, String message, String subject) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });


        try {

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}