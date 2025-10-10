package com.thanhpham.Chat.config;

import com.thanhpham.Chat.util.Infor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
    @Bean
    public ChatClient ChatClientConfig(ChatClient.Builder builder, ChatMemory chatMemory){
        return builder.defaultSystem(Infor.NAME).defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
    }

    @Bean
    public ChatMemory ChatMemoryConfig(JdbcChatMemoryRepository chatMemoryRepository){
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(25)
                .build();
    }
}
