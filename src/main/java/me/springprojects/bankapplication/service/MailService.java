package me.springprojects.bankapplication.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String[] subjects = {"Money Withdrawal", "Money Deposit", "Money Transfer"};
    private final String[] texts = {"You have withdrawned an amount of: ", "You have deposited an amount of: ", "You have transferred an amount of: "};

    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void sendWithdrawalMail(String to, BigDecimal amount){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subjects[0]);
        mail.setText(texts[0] + amount);
        mail.setTo(to);
        mailSender.send(mail);
    }

    public void sendDepositMail(String to, BigDecimal amount){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subjects[1]);
        mail.setText(texts[1] + amount);
        mail.setTo(to);
        mailSender.send(mail);
    }

    public void sendTransferMail(String to, BigDecimal amount){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subjects[2]);
        mail.setText(texts[2] + amount);
        mail.setTo(to);
        mailSender.send(mail);
    }
}
