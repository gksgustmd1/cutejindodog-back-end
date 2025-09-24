package com.example.back_end;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificationService {
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    // 인증 코드 생성
    public String generateCode(String email) {
        String code = String.valueOf((int)(Math.random() * 900000) + 100000); // 6자리 숫자
        verificationCodes.put(email, code);
        return code;
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }

    // 인증 코드 제거
    public void removeCode(String email) {
        verificationCodes.remove(email);
    }
}
