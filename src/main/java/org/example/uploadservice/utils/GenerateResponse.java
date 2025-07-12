package org.example.uploadservice.utils;

import org.example.uploadservice.model.dto.APIResponse;
import org.example.uploadservice.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;


public class GenerateResponse {
    public static ResponseEntity<?> generateErrorResponse(Integer status, String message) {
        return ResponseEntity.badRequest().body(new ErrorResponse(status, message));
    }
    public static ResponseEntity<?> generateSuccessResponse(Integer status ,String message , Object data) {
        return ResponseEntity.ok().body(new APIResponse<>(status, message, data)
        );
    }
}
