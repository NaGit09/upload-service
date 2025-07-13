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
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UploadServiceImp implements IUploadService {

    @Autowired
    private final MediaRepository mediaRepository;
    @Autowired
    private final Cloudinary cloudinary;


    @Override
    public ResponseEntity<?> uploadMedia(MultipartFile[] files) {

        // check files into request
        if (files == null || files.length == 0 ||
                Arrays.stream(files).allMatch(MultipartFile::isEmpty)) {
            return GenerateResponse.generateErrorResponse(
                    400, "No files to upload.");
        }

        List<MediaResponse> mediaResponses = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            try {
                String fileType = file.getContentType();

                if (fileType == null || !fileType.startsWith("image/")) {
                    return GenerateResponse.generateErrorResponse(
                            401, "file not supported."
                    );
                }
                // upload a file into cloud
                var uploadResult = cloudinary.uploader().upload(
                        file.getBytes(),
                        ObjectUtils.emptyMap()
                );

                // save media and return media response
                MediaResponse mediaResponse = GenerateMedia.generateMedia
                        (uploadResult, fileType, mediaRepository);

                mediaResponses.add(mediaResponse);

            } catch (Exception e) {
                System.err.println("Failed to upload file: " + file.getOriginalFilename());
            }
        }

        // return result
        if (mediaResponses.isEmpty()) {
            return GenerateResponse.generateErrorResponse(
                    500, "All file uploads failed.");
        }

        return GenerateResponse.generateSuccessResponse(
                200, "Upload success", mediaResponses);
    }

    @Override
    public ResponseEntity<?> deleteMedia(Long[] ids) throws IOException {

        if (ids == null || ids.length == 0) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files.");
        }

        for (Long id : ids) {

            Media media = mediaRepository.findById(id).orElse(null);

            if (media == null) {
                return GenerateResponse.generateErrorResponse(
                        404, "No valid media files found for given IDs."
                );
            }

            var cloudinaryApi = cloudinary.uploader().destroy
                    (media.getPublicId(), ObjectUtils.emptyMap());

            boolean isDeleted = cloudinaryApi.get("result").equals("ok");
            mediaRepository.delete(media);

            if (!isDeleted) {
                return GenerateResponse.generateErrorResponse(
                        500, "Failed to delete media file: " + media.getPublicId()
                );
            }
        }

        return GenerateResponse.generateSuccessResponse(
                200, "File deleted !", true);
    }

    @Override
    public ResponseEntity<?> updateMedia(Long[] ids) {
        // check ids return client
        if (ids == null || ids.length == 0) {
            return GenerateResponse.generateErrorResponse(
                    400, "Missing required fields or files."
            );
        }

        List<Media> medias = mediaRepository.findAllById(List.of(ids));

        if (medias.isEmpty()) {
            return GenerateResponse.generateErrorResponse(
                    404, "No valid media files found for given IDs."
            );
        }
        medias.forEach((media) -> {
            media.setUploadType(UploadType.COMPLETE);
        });
        mediaRepository.saveAll(medias);

        return GenerateResponse.generateSuccessResponse(
                200,
                "Updated " + medias.size() + " file(s) to COMPLETE.",
                true
        );
    }

    @Override
    public ResponseEntity<?> deleteDraft() {
        List<Media> draftFiles = mediaRepository.getAllDraftFile(
                UploadType.DRAFT, LocalDateTime.now().minusDays(30)
        );

        if (draftFiles == null || draftFiles.isEmpty()) {
            return GenerateResponse.generateSuccessResponse(
                    200, "No draft files", true
            );
        }

        for (Media media : draftFiles) {
            try {
                String publicId = media.getPublicId();
                if (publicId != null) {
                    cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                }
            } catch (IOException e) {
                System.err.println("Error deleting file: " + media.getId());
                e.printStackTrace();
                // Optionally continue or break
            }

            mediaRepository.delete(media);
        }

        return GenerateResponse.generateSuccessResponse(
                200, "Deleted " + draftFiles.size() + " draft file(s)", true
        );
    }



}
