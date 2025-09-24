package com.example.back_end;

import lombok.Data;

@Data
public class VerifyRequest {
    private String nickname;
    private String password;
    private String email;
    private String code;
}
