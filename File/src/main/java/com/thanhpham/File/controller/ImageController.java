package com.thanhpham.File.controller;

import com.thanhpham.File.service.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageStorageService imageStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileDownloadUri = imageStorageService.storeFile(file);
        return ResponseEntity.ok(fileDownloadUri);
    }

    @PostMapping("/uploads")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files") List<MultipartFile> files) {
        List<String> fileDownloadUri = imageStorageService.storeFiles(files);
        return ResponseEntity.ok(fileDownloadUri);
    }
}
