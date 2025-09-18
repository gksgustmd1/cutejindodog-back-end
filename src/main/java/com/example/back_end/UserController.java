package com.example.back_end;


import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // 회원가입 (등록)
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        return repo.save(user);
    }

    // 전체 회원 조회 (테스트용)
    @GetMapping
    public List<User> getAllUsers() {
        return repo.findAll();
    }
}
