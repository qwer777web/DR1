package com.dr1.auth.controller;

import com.dr1.auth.dto.LoginRequest;
import com.dr1.auth.dto.TokenResponse;
import com.dr1.auth.service.AuthService;
import com.dr1.common.core.ApiResult;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResult<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResult.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ApiResult<Map<String, Object>> currentUser() {
        return ApiResult.ok(authService.currentUser());
    }

    @GetMapping("/health")
    public ApiResult<String> health() {
        return ApiResult.ok("dr1-auth is running");
    }
}

