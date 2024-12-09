package com.integrador.sistlms.service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String to, String token) {
        String subject = "Confirma tu correo electrónico";
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
    
        // Cambiar el enlace por un botón
        String message = "<html>"
                + "<body>"
                + "<p>Haz clic en el siguiente botón para confirmar tu correo electrónico:</p>"
                + "<a href='" + confirmationUrl + "' style='"
                + "display: inline-block; "
                + "padding: 10px 20px; "
                + "font-size: 16px; "
                + "color: #fff; "
                + "background-color: #1f8247; "
                + "text-decoration: none; "
                + "border-radius: 5px; "
                + "text-align: center;'>"
                + "Validar</a>"
                + "</body>"
                + "</html>";
    
        MimeMessage mimeMessage = mailSender.createMimeMessage();
    
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true); // Especificar que el contenido es HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
    
}
