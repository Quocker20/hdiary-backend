package com.hdiary.backend.service;

import com.hdiary.backend.dto.request.LoginRequest;
import com.hdiary.backend.dto.request.UserUpdateRequest;
import com.hdiary.backend.dto.response.UserResponse;
import com.hdiary.backend.entity.User;
import com.hdiary.backend.exception.UserNotFoundException;
import com.hdiary.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    
    private final UserRepository userRepository;
    private final ImageService imageService;
    
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

    @Transactional
    public UserResponse updateProfile(Long userId, UserUpdateRequest request) {
        log.info("Attempting to update profile for user id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("Update profile failed: User not found with id: {}", userId);
                    return new UserNotFoundException("Không tìm thấy người dùng.");
                });

        if (request.displayName() != null) {
            user.setDisplayName(request.displayName());
        }
        
        if (request.username() != null) {
            user.setUsername(request.username());
        }

        if (request.avatar() != null && !request.avatar().isEmpty()) {
            ImageService.ImageUploadResult result = imageService.uploadImage(request.avatar());
            
            String oldAvatarPublicId = user.getAvatarPublicId();
            
            user.setAvatarUrl(result.url());
            user.setAvatarPublicId(result.publicId());
            
            if (oldAvatarPublicId != null) {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        imageService.deleteImage(oldAvatarPublicId);
                    }
                });
            }
        }

        User updatedUser = userRepository.save(user);
        log.info("Profile updated successfully for user id: {}", updatedUser.getId());
        return UserResponse.fromEntity(updatedUser);
    }
}
