package com.thanhpham.Chat.controller;

import com.thanhpham.Chat.dto.response.BillDetail;
import com.thanhpham.Chat.service.ChatService;
import com.thanhpham.Chat.util.ChuckUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public BillDetail chat(@RequestParam("message") String message, @RequestParam("files") List<MultipartFile> files) {
        return chatService.imageProcessing(message, files);
    }

    @PostMapping("/test")
    public String test(@RequestParam("message") String message){
        return ChuckUtil.splitByParagraphOrHeading(message).toString();
    }
}
