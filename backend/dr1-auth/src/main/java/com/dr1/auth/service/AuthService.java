package com.dr1.auth.service;

import com.dr1.auth.dto.LoginRequest;
import com.dr1.auth.dto.TokenResponse;
import com.dr1.common.exception.BusinessException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final long EXPIRES_IN = 7200L;

    public TokenResponse login(LoginRequest request) {
        if (!"admin".equals(request.getUsername()) || !"123456".equals(request.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }
        String rawToken = request.getUsername() + ":" + Instant.now().toEpochMilli();
        String token = Base64.getUrlEncoder().encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
        return new TokenResponse(token, "Bearer", request.getUsername(), EXPIRES_IN);
    }

    public Map<String, Object> currentUser() {
        Map<String, Object> user = new HashMap<>();
        user.put("username", "admin");
        user.put("nickname", "系统管理员");
        user.put("roles", new String[] {"admin"});
        return user;
    }
}

