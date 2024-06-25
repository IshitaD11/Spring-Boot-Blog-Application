package com.project.springbootblogapplication.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("upload") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            // Generate a unique file name
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Create a temporary file to upload to S3
            File tempFile = File.createTempFile("temp",fileName);
            file.transferTo(tempFile);

            // Upload file to S3
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, tempFile));

            // Delete the temporary file
            tempFile.delete();

            String fileUrl = s3Client.getUrl(bucketName,fileName).toString();

            response.put("url", fileUrl);

            // Log the file path
            logger.info("File uploaded to S3: " + fileUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}