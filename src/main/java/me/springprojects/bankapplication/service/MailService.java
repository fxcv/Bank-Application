package me.springprojects.bankapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final static String WITHDRAWAL_SUBJECT = "Money Withdrawal";
    private final static String WITHDRAWAL_TEXT = "You have successfully withdrawn money!";
    private final static String DEPOSIT_SUBJECT = "Money Deposit";
    private final static String DEPOSIT_TEXT = "You have successfully deposited money!";
    private final static String TRANSFER_SUBJECT = "Money Transfer";
    private final static String TRANSFER_TEXT = "You have successfully transferred money!";
    private final static String REGISTRATION_SUBJECT = "Account Creation";
    private final static String REGISTRATION_TEXT = "You have successfully registered an account!";

    public void sendWithdrawalMail(String to){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(WITHDRAWAL_SUBJECT);
        mail.setText(WITHDRAWAL_TEXT);
        mail.setTo(to);
        mailSender.send(mail);
    }

    public void sendDepositMail(String to){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(DEPOSIT_SUBJECT);
        mail.setText(DEPOSIT_TEXT);
        mail.setTo(to);
        mailSender.send(mail);
    }

    public void sendTransferMail(String to){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(TRANSFER_SUBJECT);
        mail.setText(TRANSFER_TEXT);
        mail.setTo(to);
        mailSender.send(mail);
    }

    public void sendAccountCreationMail(String to){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(REGISTRATION_SUBJECT);
        mail.setText(REGISTRATION_TEXT);
        mail.setTo(to);
        mailSender.send(mail);
    }
}
