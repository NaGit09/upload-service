package org.example.uploadservice.utils;

import org.example.uploadservice.constant.UploadType;
import org.example.uploadservice.model.dto.MediaResponse;
import org.example.uploadservice.model.entity.Media;

import java.util.UUID;

public class GenerateMedia {
    public static MediaResponse generateMediaResponse(String mediaUrl, String mediaType) {
        MediaResponse mediaResponse = new MediaResponse();
        mediaResponse.setMediaUrl(mediaUrl);
        mediaResponse.setMediaType(mediaType);
        mediaResponse.setUploadType(UploadType.DRAFT);
        return mediaResponse;
    }
    public static Media generateMedia(String url , String mediaType , Long postId, UUID userId, UploadType uploadType) {
        Media media = new Media();
        media.setMediaUrl(url);
        media.setMediaType(mediaType);
        media.setUploadType(UploadType.DRAFT);
        media.setPostId(postId);
        media.setUserId(userId);
        return media;
    }
}
