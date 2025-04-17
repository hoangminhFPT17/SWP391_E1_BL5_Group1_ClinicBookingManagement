/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.util.Properties;
import java.util.logging.Logger;

// Using javax.mail instead of jakarta.mail
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author ADMIN
 */
public class MailService {
    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    public static void sendMail(String email, String subject, String content) throws Exception {
        final String usermail = "ddtd281203@gmail.com";
        final String password = "nrpf gejk vaab oxvf";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port", "465");
        
        // Create authenticator with javax.mail
        javax.mail.Authenticator authenticator = new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usermail, password);
            }
        };
        
        try {
            Session session = Session.getInstance(props, authenticator);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(usermail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(content);
            
            System.out.println("Sending email to " + email);
            Transport.send(message);
            System.out.println("Email sent successfully!");
            
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }
}
