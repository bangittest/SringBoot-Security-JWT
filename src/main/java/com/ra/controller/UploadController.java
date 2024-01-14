package com.ra.controller;

import com.ra.service.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    public ResponseEntity<?>upload(@RequestParam("img")MultipartFile file){
        String upload=uploadService.uploadImage(file);
        return new ResponseEntity<>(upload, HttpStatus.OK);
    }
}
