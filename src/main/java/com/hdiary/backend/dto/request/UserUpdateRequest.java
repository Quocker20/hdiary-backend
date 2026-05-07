package com.hdiary.backend.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateRequest(
    String displayName,
    String username,
    MultipartFile avatar
) {}
