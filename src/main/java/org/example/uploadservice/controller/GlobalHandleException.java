package org.example.uploadservice.controller;

import org.example.uploadservice.utils.GenerateResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

@ControllerAdvice
public class GlobalHandleException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return GenerateResponse.generateErrorResponse(HttpStatus.PAYLOAD_TOO_LARGE.value(), exc.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOEException(MaxUploadSizeExceededException exc) {
        return GenerateResponse.generateErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage());
    }
}
