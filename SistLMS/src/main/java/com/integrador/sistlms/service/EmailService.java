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

        sendEmail(to, subject, message);
    }

    public void sendNewCourseNotification(String to, String courseName, String courseDescription, Long cursoId,
            Long usuarioId) {
        String subject = "Nuevo curso disponible: " + courseName;
        String inscriptionUrl = "http://localhost:8080/inscribirse?cursoId=" + cursoId + "&usuarioId=" + usuarioId;

        String message = "<html>"
                + "<body>"
                + "<h1>¡Un nuevo curso ha sido creado!</h1>"
                + "<p>Estamos emocionados de anunciar un nuevo curso:</p>"
                + "<h2>" + courseName + "</h2>"
                + "<p>" + courseDescription + "</p>"
                + "<p>Haz clic en el botón de abajo para inscribirte:</p>"
                + "<a href='" + inscriptionUrl + "' style='"
                + "display: inline-block; "
                + "padding: 10px 20px; "
                + "font-size: 16px; "
                + "color: #fff; "
                + "background-color: #1f8247; "
                + "text-decoration: none; "
                + "border-radius: 5px; "
                + "text-align: center;'>"
                + "Inscribirse</a>"
                + "</body>"
                + "</html>";

        sendEmail(to, subject, message);
    }

    private void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // Especificar que el contenido es HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar correo", e);
        }
    }
}
