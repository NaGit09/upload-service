package org.example.uploadservice.controller;

import org.example.uploadservice.service.UploadServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload-service/upload")
public class MediaController {
    @Autowired
    private UploadServiceImp uploadServiceImp;
    @GetMapping("/")
    public String upload() {
        return "success";
    }
}
