package org.serratec.backend.config;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Configuration
public class MailConfig {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;


    public void enviar(String para, String assunto, String nome, String texto, String informacoes) {
        try {
            Context context = new Context();
            context.setVariable("nome", nome);
            context.setVariable("texto", texto);
            context.setVariable("informacoes", informacoes.replace("\n", "<br>"));
            context.setVariable("assinatura", "DragonStore&Cia");

            String corpoHtml = templateEngine.process("EmailTemplate", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("darkdragonsstore@gmail.com");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }

}
