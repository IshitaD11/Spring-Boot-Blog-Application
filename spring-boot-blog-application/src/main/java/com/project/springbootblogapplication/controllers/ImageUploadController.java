package com.project.springbootblogapplication.controllers;

import com.project.springbootblogapplication.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("upload") MultipartFile file) {
        try {
            String imageUrl = imageUploadService.uploadImage(file);
            System.out.println("imageUrl" + imageUrl);
            System.out.println("returns" + "{ \"url\": \"" + imageUrl + "\" }");
            return ResponseEntity.ok("{ \"url\": \"" + imageUrl + "\" }");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Could not upload file");
        }
    }
}
