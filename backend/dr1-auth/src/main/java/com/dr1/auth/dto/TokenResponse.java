package com.dr1.auth.dto;

public class TokenResponse {

    private String token;
    private String tokenType;
    private String username;
    private Long expiresIn;

    public TokenResponse() {
    }

    public TokenResponse(String token, String tokenType, String username, Long expiresIn) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}

