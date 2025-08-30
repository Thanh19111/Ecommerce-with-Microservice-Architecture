package com.thanhpham.File.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageStorageService {
    private final Path fileStorageLocation;

    public ImageStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.fileStorageLocation);
    }

    public List<String> storeFiles(List<MultipartFile> files) {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID() + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            try {
                Path targetLocation = this.fileStorageLocation.resolve(fileName);
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
                list.add("/images/" + fileName);

            } catch (IOException ex) {
                throw new RuntimeException("Không thể lưu file: " + fileName, ex);
            }
        }
        return list;
    }

    public String storeFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return "/images/" + fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Không thể lưu file: " + fileName, ex);
        }
    }
}
