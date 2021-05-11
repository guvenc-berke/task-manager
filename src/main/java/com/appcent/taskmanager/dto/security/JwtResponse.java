package com.appcent.taskmanager.dto.security;

import lombok.Getter;

@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String password;

    public JwtResponse(String token, Long id, String email, String password) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
