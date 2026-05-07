package com.hdiary.backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {

    private final Cloudinary cloudinary;

    public ImageUploadResult uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String publicId = uploadResult.get("public_id").toString();
            String url = uploadResult.get("secure_url").toString();
            return new ImageUploadResult(url, publicId);
        } catch (IOException e) {
            log.error("Failed to upload image to Cloudinary", e);
            throw new RuntimeException("Tải ảnh thất bại. Vui lòng thử lại.");
        }
    }

    public void deleteImage(String publicId) {
        if (publicId == null || publicId.trim().isEmpty()) {
            return;
        }
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            log.error("Failed to delete image from Cloudinary. public_id: " + publicId, e);
            // Non-critical failure, don't block deletion logic
        }
    }

    public record ImageUploadResult(String url, String publicId) {}
}
