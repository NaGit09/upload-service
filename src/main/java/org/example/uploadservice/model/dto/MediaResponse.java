package org.example.uploadservice.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.uploadservice.constant.UploadType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class MediaResponse {
    @Column(name = "media_url", nullable = false)
    private String mediaUrl;

    @Column(name = "media_type")
    private String mediaType;

    @Enumerated(EnumType.STRING)
    @Column(name = "upload_type", length = 20)
    private UploadType uploadType;
}
