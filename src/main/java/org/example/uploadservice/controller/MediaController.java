package org.example.uploadservice.controller;

import org.example.uploadservice.service.UploadServiceImp;
import org.example.uploadservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload-service/upload")
public class MediaController {
    @Autowired
    private UploadServiceImp uploadServiceImp;

    @PostMapping("/single")
    public ResponseEntity<?> sentFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("postId") Long postId,
            @RequestParam("userId") UUID userId
    ) throws IOException {
        return uploadServiceImp.uploadMedia(file, postId, userId);
    }

    @PostMapping("/multifile")
    public ResponseEntity<?> sentFileUploads(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("postId") Long postId,
            @RequestParam("userId") UUID userId
    ) throws IOException {
        return uploadServiceImp.uploadMultiMedia(files, postId, userId);
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<?> DeleteFile(@PathVariable String publicId) throws IOException {
        if (publicId == null) {
            return GenerateResponse.generateErrorResponse(404, "public_id is required");
        }
        return uploadServiceImp.deleteMedia(publicId);
    }

    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<?> DeleteFilePost(@PathVariable Long postId) throws IOException {
        if (postId == null) {
            return GenerateResponse.generateErrorResponse(404, "public_id is required");
        }
        return uploadServiceImp.deleteByPostId(postId);
    }

    @DeleteMapping("/deletePost/{userID}")
    public ResponseEntity<?> sentFileDelete(@PathVariable UUID userID) throws IOException {
        if (userID == null) {
            return GenerateResponse.generateErrorResponse(404, "public_id is required");
        }
        return uploadServiceImp.deleteByUserId(userID);
    }
}
