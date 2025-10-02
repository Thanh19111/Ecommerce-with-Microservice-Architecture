package com.thanhpham.Chat.controller;

import com.thanhpham.Chat.dto.Message;
import com.thanhpham.Chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public String chat(@RequestBody Message message){
        return chatService.chatAI(message);
    }

    @PostMapping("/test")
    public String test(@RequestBody Message message){
        return chatService.getMessage(message);
    }


}
