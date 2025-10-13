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
    public String chat(@RequestParam("conversationId") String conversationId, @RequestParam("message") String message){
        return chatService.chat(conversationId, message);
    }

    @PostMapping("/ocr")
    public BillDetail ocr(@RequestParam("files") List<MultipartFile> files) {
        return chatService.imageProcessing(files);
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("conversationId") String conversationId, @RequestParam("message") String message, @RequestParam("files") List<MultipartFile> files){
        return chatService.chatWithImage(conversationId, message, files);
    }

    @PostMapping("/summerize")
    public String summerize(@RequestParam("conversationId") String conversationId, @RequestParam("message") String message, @RequestParam("length") String length){
        return chatService.summerize(conversationId, message, length);
    }

    @PostMapping("/search")
    public String search(@RequestParam("conversationId") String conversationId, @RequestParam("message") String message){
        return ragService.retrieveAndGenerate(conversationId, message);
    }

    @PostMapping("/embed")
    public List<Document> rag(@RequestParam("message") String message){
        return ragService.embedding(message);
    }
}
