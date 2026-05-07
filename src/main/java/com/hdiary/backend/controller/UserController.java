package com.hdiary.backend.controller;

import com.hdiary.backend.dto.request.LoginRequest;
import com.hdiary.backend.dto.request.UserUpdateRequest;
import com.hdiary.backend.dto.response.UserResponse;
import com.hdiary.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        log.info("Received login request for username: {}", request.username());
        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateProfile(
            @PathVariable Long userId,
            @ModelAttribute UserUpdateRequest request) {
        log.info("Received update profile request for user id: {}", userId);
        return ResponseEntity.ok(userService.updateProfile(userId, request));
    }
}
