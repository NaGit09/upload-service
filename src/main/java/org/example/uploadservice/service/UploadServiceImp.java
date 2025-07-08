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
            (MultipartFile file, Long postId, UUID userId)
            throws IOException {

        if (file == null || file.isEmpty() || postId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Invalid upload data.");
        }

        var uploadResult = cloudinary.uploader().
                upload(file.getBytes(), ObjectUtils.emptyMap());

        System.out.println(uploadResult.toString());
        String fileType = file.getContentType();
        MediaResponse mediaResponse = GenerateMedia.generateMedia
                (uploadResult, userId, postId, fileType, mediaRepository);

        return GenerateResponse.generateSuccessResponse(
                200, "Upload file success", mediaResponse);

    }

    @Override
    public ResponseEntity<?> uploadMultiMedia
            (MultipartFile[] files, Long postId, UUID userId)
            throws IOException {

        if (files == null || postId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files.");
        }

        List<MediaResponse> mediaResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String fileType = file.getContentType();
            MediaResponse mediaResponse = GenerateMedia.generateMedia
                    (uploadResult, userId, postId, fileType, mediaRepository);
            mediaResponses.add(mediaResponse);
        }

        return GenerateResponse.generateSuccessResponse
                (200, "Upload multiple files success", mediaResponses);
    }


    @Override
    public ResponseEntity<?> deleteMedia(String publicId) throws IOException {
        Optional<Media> mediaOpt = mediaRepository.findByPublicId(publicId);
        if (mediaOpt.isEmpty()) {
            return GenerateResponse.generateErrorResponse(401, "find not found media ");
        }
        Media media = mediaOpt.get();

        var deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        boolean result = "ok".equals(deleteResult.get("result"));

        mediaRepository.delete(media);
        if (!result) {
            return GenerateResponse.generateErrorResponse(401, "delete media failed");
        }

        return GenerateResponse.generateSuccessResponse(
                200, "deleted successfully", true
        );
    }

    @Override
    public ResponseEntity<?> deleteMultiMedia(String publicId, UUID userId) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteByPostId(Long postId) throws IOException {
        Optional<Media> mediaOpt = mediaRepository.findByPostId(postId);
        if (mediaOpt.isEmpty()) {
            return GenerateResponse.generateErrorResponse(401, "find not found media ");
        }
        Media media = mediaOpt.get();
        String publicId = media.getPublicId();
        var deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        boolean result = "ok".equals(deleteResult.get("result"));
        mediaRepository.delete(media);
        if (!result) {
            return GenerateResponse.generateErrorResponse(401, "delete media failed");

        }
        return GenerateResponse.generateSuccessResponse(
                200, "deleted successfully", true
        );
    }

    @Override
    public ResponseEntity<?> deleteByUserId(UUID userId) throws IOException {
        Optional<Media> mediaOpt = mediaRepository.findByUserId(userId);
        if (mediaOpt.isEmpty()) {
            return GenerateResponse.generateErrorResponse(401, "find not found media ");
        }
        Media media = mediaOpt.get();
        String publicId = media.getPublicId();
        var deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        boolean result = "ok".equals(deleteResult.get("result"));
        mediaRepository.delete(media);
        if (!result) {
            return GenerateResponse.generateErrorResponse(401, "delete media failed");

        }
        return GenerateResponse.generateSuccessResponse(
                200, "deleted successfully", true
        );
    }

    @Override
    public ResponseEntity<?> getMediaPost(Long postId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAvatar(UUID userId) {
        return null;
    }
}
