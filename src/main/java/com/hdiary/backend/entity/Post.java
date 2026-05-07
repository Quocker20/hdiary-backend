package com.hdiary.backend.entity;

import com.hdiary.backend.entity.enums.EventCategory;
import com.hdiary.backend.entity.enums.Mood;
import com.hdiary.backend.entity.enums.PostType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts", indexes = {
        @Index(name = "idx_user_created", columnList = "user_id, created_at DESC"),
        @Index(name = "idx_created_at", columnList = "created_at DESC"),
        @Index(name = "idx_mood", columnList = "mood")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_type")
    @Builder.Default
    private PostType postType = PostType.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_category")
    private EventCategory eventCategory;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Mood mood;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "image_public_id", length = 100)
    private String imagePublicId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
