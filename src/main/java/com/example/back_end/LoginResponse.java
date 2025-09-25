package com.example.back_end;

public class LoginResponse {
    private String nickname;

    public LoginResponse(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() { return nickname; }
}
