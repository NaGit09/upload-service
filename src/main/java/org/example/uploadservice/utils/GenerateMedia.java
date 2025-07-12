package org.example.uploadservice.utils;

import org.example.uploadservice.constant.UploadType;
import org.example.uploadservice.model.dto.MediaResponse;
import org.example.uploadservice.model.entity.Media;
import org.example.uploadservice.model.repository.MediaRepository;

import java.util.Map;
import java.util.UUID;

public class GenerateMedia {
    public static MediaResponse generateMediaResponse(Media media) {

        if (media == null) {
            return new MediaResponse();
        }

        return MediaResponse.builder()
                .id(media.getId())
                .mediaUrl(media.getMediaUrl())
                .mediaType(media.getMediaType())
                .build();
    }

    public static MediaResponse generateMedia
            (Map resul, String mediaType, MediaRepository mediaRepository) {

        String url = resul.get("secure_url").toString();
        String publicId = resul.get("public_id").toString();

        Media media = mediaRepository.save(
                Media.builder()
                        .mediaUrl(url)
                        .publicId(publicId)
                        .mediaType(mediaType)
                        .uploadType(UploadType.DRAFT)
                        .build());

        return generateMediaResponse(media);
    }
}
