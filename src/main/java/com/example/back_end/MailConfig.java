package com.example.back_end;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class MailConfig {
    @Bean
    public JavaMailSender javaMailSender() {
        // 가짜 JavaMailSender (설정 없음, 메일 실제로는 안 나감)
        return new JavaMailSenderImpl();
    }
}
