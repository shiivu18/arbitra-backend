package com.arbitra.backend.controller;

import com.arbitra.backend.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider tokenProvider;

    public AuthController(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(
            @RequestParam String username,
            @RequestParam(defaultValue = "MERCHANT") String role) {
        String jwt = tokenProvider.generateToken(username, role);
        return ResponseEntity.ok(Map.of("accessToken", jwt, "tokenType", "Bearer"));
    }
}