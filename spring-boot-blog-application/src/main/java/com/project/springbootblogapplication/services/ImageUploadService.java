package com.project.springbootblogapplication.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final String uploadDir = "uploads/";

    public String uploadImage(MultipartFile file) throws IOException {
        try{
            if (file.isEmpty()) {
                throw new IOException("Failed to store empty file");
            }
            String fileExtension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
            System.out.println("fileExtension " + fileExtension);
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            System.out.println("fileName" + fileName);
            Path uploadPath = Paths.get(uploadDir).resolve(fileName);
            System.out.println("uploadPath" + uploadPath);
            Files.createDirectories(uploadPath.getParent());
            System.out.println("uploadPath" + uploadPath);

            Files.copy(file.getInputStream(), uploadPath);
            return "/uploads/" + fileName;
        }
        catch (IOException e) {
            e.printStackTrace(); // Log the exception
            return null; // Return null to indicate failure)
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1);
    }
}
