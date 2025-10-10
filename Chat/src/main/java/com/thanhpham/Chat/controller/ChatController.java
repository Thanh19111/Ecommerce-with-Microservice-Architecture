package com.thanhpham.Chat.controller;

import com.thanhpham.Chat.dto.response.BillDetail;
import com.thanhpham.Chat.service.ChatService;
import com.thanhpham.Chat.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final RagService ragService;

    @PostMapping
    public String chat(@RequestParam("message") String message){
        return chatService.chat(message);
    }

    @PostMapping("/orc")
    public BillDetail orc(@RequestParam("message") String message, @RequestParam("files") List<MultipartFile> files) {
        return chatService.imageProcessing(message, files);
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("message") String message, @RequestParam("files") List<MultipartFile> files){
        return chatService.chatWithImage(message, files);
    }

    @PostMapping("/summerize")
    public String summerize(@RequestParam("message") String message, @RequestParam("length") String length){
        return chatService.summerize(message, length);
    }

    @PostMapping("/test")
    public String test(@RequestParam("message") String message){
        return ragService.retrieveAndGenerate(message);
    }

    @PostMapping("/rag")
    public List<Document> rag(@RequestParam("message") String message){
        return ragService.embedding(message);
    }
}
