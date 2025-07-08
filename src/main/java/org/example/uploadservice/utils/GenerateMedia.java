package org.example.uploadservice.utils;

import org.example.uploadservice.constant.UploadType;
import org.example.uploadservice.model.dto.MediaResponse;
import org.example.uploadservice.model.entity.Media;
import org.example.uploadservice.model.repository.MediaRepository;

import java.util.Map;
import java.util.UUID;

public class GenerateMedia {
    public static MediaResponse generateMediaResponse(String mediaUrl, String mediaType , String publicId) {
        MediaResponse mediaResponse = new MediaResponse();
        mediaResponse.setMediaUrl(mediaUrl);
        mediaResponse.setMediaType(mediaType);
        mediaResponse.setUploadType(UploadType.DRAFT);
        mediaResponse.setPublicID(publicId);

        return mediaResponse;
    }

    public static MediaResponse generateMedia(Map resul, UUID userId, Long postId,
                                      String mediaType, MediaRepository mediaRepository) {

        String url = resul.get("secure_url").toString();
        String publicId = resul.get("public_id").toString();

        Media media = new Media();
        media.setMediaUrl(url);
        media.setMediaType(mediaType);
        media.setUploadType(UploadType.DRAFT);
        media.setPublicId(publicId);
        media.setPostId(postId);
        media.setUserId(userId);
        mediaRepository.save(media);
        return generateMediaResponse(url,mediaType, publicId);
    }
}
