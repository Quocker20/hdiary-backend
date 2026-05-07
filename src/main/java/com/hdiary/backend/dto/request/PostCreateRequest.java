package com.hdiary.backend.dto.request;

import com.hdiary.backend.entity.enums.EventCategory;
import com.hdiary.backend.entity.enums.Mood;
import com.hdiary.backend.entity.enums.PostType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PostCreateRequest(
    @NotNull(message = "User ID không được để trống")
    Long userId,
    
    @NotNull(message = "Loại bài viết không được để trống")
    PostType postType,
    
    EventCategory eventCategory,
    
    @NotNull(message = "Tâm trạng không được để trống")
    Mood mood,
    
    String content,
    
    MultipartFile imageFile
) {}
