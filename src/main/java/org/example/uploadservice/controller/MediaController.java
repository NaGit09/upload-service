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
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (file == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }
        return uploadServiceImp.uploadMedia(file);
    }

    @PostMapping("/multifile")
    public ResponseEntity<?> sentFileUploads(
            @RequestParam("files") MultipartFile[] files
    ) throws IOException {
        if (files == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }
        return uploadServiceImp.uploadMultiMedia(files);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteFile(@PathVariable Long id) throws IOException {
        if (id == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }
        return uploadServiceImp.deleteMedia(id);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> DeleteAllFiles(@RequestBody Long[] ids) throws IOException {
        if (ids == null) {
            return GenerateResponse.generateErrorResponse(
                    404, "public_id is required");
        }

        return uploadServiceImp.deleteMultiMedia(ids);

    }


}
