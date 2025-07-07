package org.example.uploadservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.example.uploadservice.constant.UploadType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImp implements IUploadService {
    @Autowired
    private final MediaRepository mediaRepository;
    @Autowired
    private final Cloudinary cloudinary;


    @Override
    public ResponseEntity<?> uploadMedia(MultipartFile file, Long postId, UUID userId) throws IOException {
        if (file == null || file.isEmpty() || postId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Invalid upload data.");
        }

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        String url = uploadResult.get("secure_url").toString();
        String fileType = file.getContentType();

        // Enum mặc định có thể là DRAF, có thể truyền từ client nếu muốn linh hoạt hơn
        UploadType uploadType = UploadType.DRAFT;

        Media media = GenerateMedia.generateMedia(url, fileType, postId, userId, uploadType);
        mediaRepository.save(media);

        return GenerateResponse.generateSuccessResponse(
                200, "Upload file success",
                GenerateMedia.generateMediaResponse(url, fileType)
        );
    }

    @Override
    public ResponseEntity<?> uploadMultiMedia(List<MultipartFile> files, Long postId, UUID userId) throws IOException {
        if (files == null || postId == null || userId == null) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files.");
        }

        List<MediaResponse> mediaResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("secure_url").toString();
            String fileType = file.getContentType();

            Media media = GenerateMedia.generateMedia(url, fileType, postId, userId, UploadType.DRAFT);
            mediaRepository.save(media);
           MediaResponse mediaResponse = GenerateMedia.generateMediaResponse(url, fileType);
           mediaResponses.add(mediaResponse);
        }

        return GenerateResponse.generateSuccessResponse(200, "Upload multiple files success", mediaResponses);
    }


    @Override
    public ResponseEntity<?> deleteMedia(Long id) {
        return null;
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
