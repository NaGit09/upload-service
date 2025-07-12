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

    private Long id;
    private String mediaUrl;
    private String mediaType;

}
