package com.example.back_end;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 기본 키 (자동 증가)

    private String nickname;  // 닉네임
    private String password;  // 비밀번호
    private String email;     // 이메일
}
