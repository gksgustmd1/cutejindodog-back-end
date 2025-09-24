package com.example.back_end;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender mailSender;

    private final String from = "gksgustmd1@gmail.com"; // 본인 Gmail 주소

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);   // ✅ 반드시 지정해야 함
            message.setTo(to);
            message.setSubject("회원가입 인증 코드");
            message.setText("인증 코드는: " + code);

            // ✅ 실제 메일 발송
            mailSender.send(message);

            System.out.println("[INFO] 메일 전송 성공 → " + to + " / 코드: " + code);
        } catch (Exception e) {
            System.err.println("[ERROR] 메일 전송 실패: " + e.getMessage());
        }
    }
}
