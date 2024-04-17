package com.AlexandreLoiola.AccessManagement.service;

import com.AlexandreLoiola.AccessManagement.repository.MailRepository;
import com.AlexandreLoiola.AccessManagement.rest.form.EmailForm;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private MailRepository mailRepository;
    private JavaMailSender mailSender;

    public MailService(MailRepository mailRepository, JavaMailSender mailSender) {
        this.mailRepository = mailRepository;
        this.mailSender = mailSender;
    }

    public void sendWelcomeMail(EmailForm emailForm) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("test@mail.com");
            helper.setTo(emailForm.getToEmail());
            helper.setSubject(emailForm.getSubject());
            String htmlMsg = mailRepository.findBySubjectAndIsActiveTrue("WelcomeNewUser").orElseThrow(() -> new IllegalStateException("Fail")).getBody();

            helper.setText(htmlMsg, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Falha ao enviar e-mail", e);
        }
    }

    public void sendRecoverPass(EmailForm emailForm, String token) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setFrom("test@mail.com");
            helper.setTo(emailForm.getToEmail());
            helper.setSubject(emailForm.getSubject());

            String htmlMsg = mailRepository.findBySubjectAndIsActiveTrue("RecoverPass").orElseThrow(() -> new IllegalStateException("Fail")).getBody();

            helper.setText(htmlMsg, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new IllegalStateException("Falha ao enviar e-mail", e);
        }
    }
}