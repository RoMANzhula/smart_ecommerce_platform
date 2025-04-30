package org.romanzhula.notification_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${spring.mail.username}")
    private String senderOfMail;

    private final JavaMailSender javaMailSender;

    
    @Transactional
    public void sendByMail(
            String addressTo,
            String topic,
            String textMessage
    ) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderOfMail);
        mailMessage.setTo(addressTo);
        mailMessage.setSubject(topic);
        mailMessage.setText(textMessage);

        javaMailSender.send(mailMessage);
    }

}
