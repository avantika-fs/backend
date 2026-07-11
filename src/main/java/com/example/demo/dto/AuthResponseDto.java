package com.example.demo.dto;

import com.example.demo.entity.enums.Role;

public class AuthResponseDto {
    private String token;
    private String username;
    private Role role;

    private AuthResponseDto(Builder builder) {
        this.token = builder.token;
        this.username = builder.username;
        this.role = builder.role;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private String username;
        private Role role;

        public Builder token(String token) { this.token = token; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public AuthResponseDto build() { return new AuthResponseDto(this); }
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public Role getRole() { return role; }
}
