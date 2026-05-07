package com.hdiary.backend.service;

import com.hdiary.backend.dto.request.LoginRequest;
import com.hdiary.backend.dto.response.UserResponse;
import com.hdiary.backend.entity.User;
import com.hdiary.backend.exception.UserNotFoundException;
import com.hdiary.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    
    public UserResponse login(LoginRequest request) {
        log.info("Attempting to login user: {}", request.username());
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> {
                    log.warn("Login failed: User not found with username: {}", request.username());
                    return new UserNotFoundException("Không tìm thấy người dùng với username này.");
                });
        log.info("Login successful for user id: {}", user.getId());
        return UserResponse.fromEntity(user);
    }
}
