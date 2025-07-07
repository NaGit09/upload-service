package org.example.uploadservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.uploadservice.constant.UploadType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "media")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id" , nullable = false)
    private UUID userId;
    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "media_type")
    private String mediaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_type", length = 20)
    private UploadType uploadType;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
