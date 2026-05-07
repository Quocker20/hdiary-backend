package com.hdiary.backend.service;

import com.hdiary.backend.dto.request.PostCreateRequest;
import com.hdiary.backend.dto.response.PostResponse;
import com.hdiary.backend.dto.response.SliceResponse;
import com.hdiary.backend.entity.Post;
import com.hdiary.backend.entity.User;
import com.hdiary.backend.exception.PostNotFoundException;
import com.hdiary.backend.exception.UserNotFoundException;
import com.hdiary.backend.repository.PostRepository;
import com.hdiary.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserNotFoundException("Không tìm thấy người dùng."));

        String imageUrl = null;
        String imagePublicId = null;

        if (request.imageFile() != null && !request.imageFile().isEmpty()) {
            ImageService.ImageUploadResult result = imageService.uploadImage(request.imageFile());
            imageUrl = result.url();
            imagePublicId = result.publicId();
        }

        Post post = Post.builder()
                .user(user)
                .postType(request.postType())
                .eventCategory(request.eventCategory())
                .mood(request.mood())
                .content(request.content())
                .imageUrl(imageUrl)
                .imagePublicId(imagePublicId)
                .build();

        Post savedPost = postRepository.save(post);
        return PostResponse.fromEntity(savedPost);
    }

    @Transactional(readOnly = true)
    public SliceResponse<PostResponse> getGlobalTimeline(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Post> postSlice = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        return SliceResponse.from(postSlice.map(PostResponse::fromEntity));
    }

    @Transactional(readOnly = true)
    public SliceResponse<PostResponse> getUserTimeline(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<Post> postSlice = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return SliceResponse.from(postSlice.map(PostResponse::fromEntity));
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Không tìm thấy bài viết."));
                
        if (post.getImagePublicId() != null) {
            imageService.deleteImage(post.getImagePublicId());
        }
        
        postRepository.delete(post);
    }
}
