package com.hdiary.backend.repository;

import com.hdiary.backend.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = { "user" })
    Slice<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = { "user" })
    Slice<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
