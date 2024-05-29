package com.project.springbootblogapplication.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Value("${upload.dir}")
    private String uploadDir;

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("upload") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Ensure upload directory exists
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Save the file locally
            File uploadFile = new File(uploadDir + File.separator + fileName);
            file.transferTo(uploadFile);

            // Log the file path
            logger.info("File saved to: " + uploadFile.getAbsolutePath());

            // Return the file URL
            String fileUrl = "/uploads/" + fileName;
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}