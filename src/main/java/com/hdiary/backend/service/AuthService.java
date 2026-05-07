package com.hdiary.backend.service;

import com.hdiary.backend.dto.request.LoginRequest;
import com.hdiary.backend.dto.response.UserResponse;
import com.hdiary.backend.entity.User;
import com.hdiary.backend.exception.UserNotFoundException;
import com.hdiary.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UserRepository userRepository;
    
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng với username này."));
        return UserResponse.fromEntity(user);
    }
}
