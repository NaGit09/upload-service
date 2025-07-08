package org.example.uploadservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IUploadService {
    ResponseEntity<?> uploadMedia(MultipartFile file, Long postId, UUID userId) throws IOException;

    ResponseEntity<?> uploadMultiMedia(MultipartFile[] files, Long postId, UUID userId) throws IOException;

    ResponseEntity<?> deleteMedia(String publicId) throws IOException;

    ResponseEntity<?> deleteMultiMedia(String publicId, UUID userId) throws IOException;

    ResponseEntity<?> deleteByPostId(Long postId) throws IOException;

    ResponseEntity<?> deleteByUserId(UUID userId) throws IOException;

    ResponseEntity<?> getMediaPost(Long postId);

    ResponseEntity<?> getAvatar(UUID userId);
}
