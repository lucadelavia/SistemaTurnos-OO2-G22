package com.sistematurnos.service.implementation;

import java.util.Map;

import com.sistematurnos.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements IEmailService{
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void enviarEmailConHtml(String para, String asunto, String nombreTemplate, Map<String, Object> variables) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variables);
            String contenidoHtml = templateEngine.process(nombreTemplate, context);

            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(contenidoHtml, true);
            helper.setFrom("unlaturnos@gmail.com");

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al enviar el correo electrónico", e);
        }
    }
}