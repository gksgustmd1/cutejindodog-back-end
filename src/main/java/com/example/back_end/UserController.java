package com.example.back_end;


import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository repo;
    private final VerificationService verificationService;
    private final MailService mailService;

    public UserController(UserRepository repo, VerificationService verificationService, MailService mailService) {
        this.repo = repo;
        this.verificationService = verificationService;
        this.mailService = mailService;
    }
    // 1단계: 이메일 인증 코드 요청
    @PostMapping("/request-signup")
    public String requestSignup(@RequestBody User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 인증 코드 생성 & 저장
        String code = verificationService.generateCode(user.getEmail());

        // 메일 발송
        mailService.sendVerificationEmail(user.getEmail(), code);

        return "인증 코드가 이메일로 발송되었습니다.";
    }

    // 2단계: 인증 코드 검증 후 최종 회원가입
    @PostMapping("/verify")
    public User verify(@RequestBody VerifyRequest request) {
        if (!verificationService.verifyCode(request.getEmail(), request.getCode())) {
            throw new RuntimeException("인증 코드가 올바르지 않습니다.");
        }

        // 인증 성공 시 DB 저장
        User user = new User(null, request.getNickname(), request.getPassword(), request.getEmail());
        User savedUser = repo.save(user);

        // 코드 삭제
        verificationService.removeCode(request.getEmail());

        return savedUser;
    }

    // 전체 회원 조회 (테스트용)
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
