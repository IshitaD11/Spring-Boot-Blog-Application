package com.project.springbootblogapplication.services;

import com.project.springbootblogapplication.DTOs.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseStatus sendEmail(String fromEmail, String toEmail, String subject, String emailBody) {
        try{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(fromEmail);
            msg.setTo(toEmail);
            msg.setSubject(subject);
            msg.setText(emailBody);
            javaMailSender.send(msg);
            return ResponseStatus.SUCCESS;
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseStatus.FAILURE;
        }
    }
}
