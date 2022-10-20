package com.bank.jwtapi.bankjwtapi.service.impl;

import com.bank.jwtapi.bankjwtapi.service.CardRequestService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CardRequestServiceImpl implements CardRequestService {

    @Value("{$spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    public CardRequestServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendVerificationCode(String email, String code) throws MessagingException {
        String toAddress = email;
//        String fromAddress = sender;
        String subject = "Please verify your action";
        String content = "Dear user,<br>"
                + "Please enter this verification code:<br>"
                + "<h3>" + code + "</h3>"
                + "Thank you,<br>"
                + "Your OpaVasStore.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom(sender);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);

//        content = content.replace("[[code]]", code);
        messageHelper.setText(content, true);
        mailSender.send(message);
    }
}
