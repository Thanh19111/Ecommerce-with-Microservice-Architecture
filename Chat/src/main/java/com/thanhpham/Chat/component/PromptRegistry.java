package com.thanhpham.Chat.component;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class PromptRegistry {
    private final Map<String, String> prompts = new HashMap<>();

    public PromptRegistry() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:/templates/*.st");

        for (Resource resource : resources) {
            String name = Objects.requireNonNull(resource.getFilename()).replace(".st", "");
            String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
            prompts.put(name, content);
        }
    }

    public PromptTemplate get(String name) {
        String template = prompts.get(name);
        if (template == null) {
            throw new IllegalArgumentException("Prompt not found: " + name);
        }
        return new PromptTemplate(template);
    }
}
