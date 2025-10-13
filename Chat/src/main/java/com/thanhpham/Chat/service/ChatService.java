package com.thanhpham.Chat.service;

import com.thanhpham.Chat.component.PromptRegistry;
import com.thanhpham.Chat.dto.response.BillDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.content.Media;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final PromptRegistry promptRegistry;
    private final ChatClient chatClient;

    public String summerize(String conversationId, String content, String length){
        PromptTemplate template = promptRegistry.get("summarize");

        return chatClient
                .prompt(template.render(Map.of("length", length, "content", content)))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call().content();
    }

    public String chat(String conversationId, String message){
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(message).call().content();
    }

    public String chatWithImage(String conversationId, String message, List<MultipartFile> files){
        List<Media> media = files.stream().map(
                img -> new Media(MimeTypeUtils.IMAGE_PNG, img.getResource())
        ).toList();

        UserMessage userMessage = UserMessage.builder()
                .media(media)
                .text(message).build();

        return chatClient.prompt()
               .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
               .messages(userMessage).call().content();
    }

    public BillDetail imageProcessing(List<MultipartFile> files){

        List<Media> media = files.stream().map(
                img -> new Media(MimeTypeUtils.IMAGE_PNG, img.getResource())
        ).toList();

        UserMessage userMessage = UserMessage.builder()
                .text("Get detail from image")
                .media(media).build();

        return chatClient.prompt()
                .messages(userMessage).call().entity(new ParameterizedTypeReference<>() {
        });
    }

}
