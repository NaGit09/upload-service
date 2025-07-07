package org.example.uploadservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface IUploadService {
    ResponseEntity<?> uploadMedia (MultipartFile file, Long postId , UUID userId) throws IOException;
    ResponseEntity<?> uploadMultiMedia (List<MultipartFile> files, Long postId , UUID userId) throws IOException;
    ResponseEntity<?> deleteMedia (Long id);
    ResponseEntity<?> getMediaPost (Long postId);
    ResponseEntity<?> getAvatar (UUID userId);
}
