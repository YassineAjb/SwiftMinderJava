package org.example.services.Election;

import org.example.config.EmailConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSenderService {

    EmailConfig emailConfig = new EmailConfig();

    public void sendEmail(String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", emailConfig.getHost());
        properties.put("mail.smtp.port", String.valueOf(emailConfig.getPort()));

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getUsername()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("yassine.ajbouni@esprit.tn"));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully to: yassine.ajbouni@esprit.tn");

        } catch (MessagingException e) {
            System.out.println("Failed to send email.");
        }
    }

}