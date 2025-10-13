package com.thanhpham.Chat.service;

import com.thanhpham.Chat.component.PromptRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final PromptRegistry promptRegistry;

    public List<Document> embedding(String message) {
        List<Document> documents = List.of(
                new Document(message, Map.of("meta2", "meta2")));

        vectorStore.add(documents);
        return documents;
    }

    public String retrieveAndGenerate(String conversationId, String message) {
        List<Document> similarDocuments = vectorStore
                .similaritySearch(SearchRequest
                        .builder()
                        .query(message)
                        .topK(4)
                        .build());

        String information = "no content";
        System.out.println(">>> Similar documents: " + similarDocuments);
        if(similarDocuments != null){
            information = similarDocuments.stream()
                    .map(Document::getText)
                    .collect(Collectors.joining("\n"));
        }

        System.out.println(">>> Prompt: " + information);
        PromptTemplate template = promptRegistry.get("rag-prompt");
        return chatClient
                .prompt(template.render(Map.of("information", information, "question", message)))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call().content();
    }

}
