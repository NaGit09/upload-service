package org.example.uploadservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IUploadService {

    ResponseEntity<?> uploadMedia(MultipartFile[] files) throws IOException;

    ResponseEntity<?> deleteMedia(Long [] ids ) throws IOException;

    ResponseEntity <?> updateMedia(Long [] ids ) ;

    ResponseEntity <?> deleteDraft() ;

}
