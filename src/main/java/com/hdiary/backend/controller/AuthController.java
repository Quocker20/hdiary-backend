package com.hdiary.backend.controller;

import com.hdiary.backend.dto.request.LoginRequest;
import com.hdiary.backend.dto.response.UserResponse;
import com.hdiary.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Received login request for username: {}", request.username());
        return ResponseEntity.ok(authService.login(request));
    }
}
