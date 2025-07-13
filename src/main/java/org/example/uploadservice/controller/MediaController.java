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

    @PostMapping("/files")
    public ResponseEntity<?> sentFileUploads(
            @RequestParam("files") MultipartFile[] files) {
        if (files == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }
        return uploadServiceImp.uploadMedia(files);
    }


    @DeleteMapping("/delete-all")
    public ResponseEntity<?> DeleteAllFiles(@RequestBody Long[] ids) throws IOException {
        if (ids == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }

        return uploadServiceImp.deleteMedia(ids);

    }

    @GetMapping("/delete-draft")
    public ResponseEntity<?> deleteDraft() {
        return uploadServiceImp.deleteDraft();
    }
    @PutMapping("/update-draft")
    public ResponseEntity<?> updateDraft(@RequestBody Long[] ids) {
        if (ids == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required"
            );

        }
        return uploadServiceImp.updateMedia(ids);
    }
}
