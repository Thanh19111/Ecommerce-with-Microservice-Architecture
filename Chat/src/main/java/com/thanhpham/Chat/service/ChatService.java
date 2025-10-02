package com.thanhpham.Chat.service;

import com.thanhpham.Chat.dto.Message;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService (ChatClient.Builder builder){
        chatClient = builder.build();
    }

    public String chatAI(Message message){
        return chatClient.prompt(message.message()).call().content();
    }

    public String getMessage(Message message){
        return message.message();
    }
}
