package com.hdiary.backend.controller;

import com.hdiary.backend.dto.request.PostCreateRequest;
import com.hdiary.backend.dto.response.PostResponse;
import com.hdiary.backend.dto.response.SliceResponse;
import com.hdiary.backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @ModelAttribute PostCreateRequest request) {
        log.info("Received create post request for user id: {}", request.userId());
        return ResponseEntity.ok(postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<SliceResponse<PostResponse>> getGlobalTimeline(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching global timeline page: {}, size: {}", page, size);
        return ResponseEntity.ok(postService.getGlobalTimeline(page, size));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<SliceResponse<PostResponse>> getUserTimeline(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching timeline for user id: {}, page: {}, size: {}", userId, page, size);
        return ResponseEntity.ok(postService.getUserTimeline(userId, page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("Received delete post request for post id: {}", id);
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
