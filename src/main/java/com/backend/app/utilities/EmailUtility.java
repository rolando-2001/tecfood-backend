package com.backend.app.utilities;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailUtility {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);
        javaMailSender.send(message);
    }

    public void sendEmailWithAttachment(String fileName, String to, String subject, String text, String attachmentUrl)  {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom(from);

            // Download the attachment PDF from the URL
            UrlResource resource = new UrlResource(attachmentUrl);
            helper.addAttachment(fileName + ".pdf", resource);

            System.out.println("Sending email with attachment..." + from);

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Error sending email with attachment");
        }

    }


}
