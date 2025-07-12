package org.example.uploadservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.uploadservice.model.dto.MediaResponse;
import org.example.uploadservice.model.entity.Media;
import org.example.uploadservice.model.repository.MediaRepository;
import org.example.uploadservice.utils.GenerateMedia;
import org.example.uploadservice.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UploadServiceImp implements IUploadService {

    @Autowired
    private final MediaRepository mediaRepository;
    @Autowired
    private final Cloudinary cloudinary;


    @Override
    public ResponseEntity<?> uploadMedia
            (MultipartFile file)
            throws IOException {

        if (file == null || file.isEmpty()) {
            return GenerateResponse.generateErrorResponse(
                    400, "Invalid upload data.");
        }

        var uploadResult = cloudinary.uploader().
                upload(file.getBytes(), ObjectUtils.emptyMap());

        String fileType = file.getContentType();

        MediaResponse mediaResponse = GenerateMedia.generateMedia
                (uploadResult, fileType, mediaRepository);

        return GenerateResponse.generateSuccessResponse(
                200, "Upload file success", mediaResponse);

    }

    @Override
    public ResponseEntity<?> uploadMultiMedia
            (MultipartFile[] files)
            throws IOException {

        if (files == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files.");
        }

        List<MediaResponse> mediaResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            var uploadResult = cloudinary.uploader().
                    upload(file.getBytes(), ObjectUtils.emptyMap());

            String fileType = file.getContentType();

            MediaResponse mediaResponse = GenerateMedia.generateMedia
                    (uploadResult, fileType, mediaRepository);
            mediaResponses.add(mediaResponse);
        }

        return GenerateResponse.generateSuccessResponse
                (200, "Upload multiple files success", mediaResponses);
    }


    @Override
    public ResponseEntity<?> deleteMultiMedia(Long[] ids) {

        if (ids == null || ids.length == 0) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files.");
        }

        Arrays.stream(ids).forEach(mediaRepository::deleteById);

        return GenerateResponse.generateSuccessResponse(
                200, "File deleted !", true);
    }


    @Override
    public ResponseEntity<?> deleteMedia(Long id) {
        if (id == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files."
            );
        }
        mediaRepository.deleteById(id);
        return GenerateResponse.generateSuccessResponse(
                200, "File deleted !", true);

    }


}
