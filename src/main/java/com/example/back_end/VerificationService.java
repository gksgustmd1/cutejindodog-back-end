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
        String savedCode = verificationCodes.get(email);

        System.out.println("==== 코드 검증 시도 ====");
        System.out.println("서버 저장 코드: [" + savedCode + "]");
        System.out.println("사용자 입력 코드: [" + code + "]");
        // 프론트에서 코드가 null이거나, 저장된 코드가 없으면 false
        if (code == null || savedCode == null) {
            return false;
        }

        return savedCode.equals(code);
    }

    // 인증 코드 제거
    public void removeCode(String email) {
        verificationCodes.remove(email);
    }
}
