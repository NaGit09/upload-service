package org.example.uploadservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUploadService {
    ResponseEntity<?> uploadMedia(MultipartFile file) throws IOException;

    ResponseEntity<?> uploadMultiMedia(MultipartFile[] files) throws IOException;

    ResponseEntity<?> deleteMedia(Long id) throws IOException;

    ResponseEntity<?> deleteMultiMedia(Long [] id ) throws IOException;

}
