package com.example.back_end;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        if (repo.findByNickname(user.getNickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        String code = verificationService.generateCode(user.getEmail());
        mailService.sendVerificationEmail(user.getEmail(), code);

        return "인증 코드가 이메일로 발송되었습니다.";
    }

    // 2단계: 인증 코드 검증 후 최종 회원가입 (닉네임 + 비밀번호만 저장)
    @PostMapping("/verify")
    public User verify(@RequestBody VerifyRequest request) {
        if (!verificationService.verifyCode(request.getEmail(), request.getCode())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증 코드가 올바르지 않습니다.");
        }

        User user = new User(null, request.getNickname(), request.getPassword(), null);
        User savedUser = repo.save(user);

        verificationService.removeCode(request.getEmail());
        return savedUser;
    }

    // 로그인 (닉네임 + 비밀번호 비교)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = repo.findByNickname(request.getNickname())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "존재하지 않는 닉네임입니다."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok(new LoginResponse(user.getNickname()));
    }

    // 전체 회원 조회 (테스트용)
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
