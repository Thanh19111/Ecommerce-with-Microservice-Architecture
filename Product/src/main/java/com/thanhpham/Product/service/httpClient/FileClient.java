package com.thanhpham.Product.service.httpClient;

import com.thanhpham.Product.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(name = "file", url = "http://localhost:8010/images", configuration = FeignClientConfig.class)
public interface FileClient {
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String createThumbnail (@RequestPart("file") MultipartFile file);

    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<String> createProductImages(@RequestPart("files") List<MultipartFile> files);
}