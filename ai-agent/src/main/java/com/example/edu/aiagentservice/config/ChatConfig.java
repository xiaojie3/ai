package com.example.edu.aiagentservice.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaEmbeddingOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    @Value("${spring.ai.ollama.embedding.base-url}")
    private String embeddingBaseUrl;

    @Value("${spring.ai.ollama.embedding.options.model}")
    private String embeddingModel;

    @Value("${spring.ai.model.chat}")
    private String model;

    @Bean
    public ChatClient chatClient(ChatClient ollamaChatClient) {
        return ollamaChatClient;
    }

    @Bean
    public EmbeddingModel embeddingModel() {
        OllamaApi ollamaApi = OllamaApi.builder().baseUrl(embeddingBaseUrl).build();
        return OllamaEmbeddingModel.builder().ollamaApi(ollamaApi).defaultOptions(OllamaEmbeddingOptions.builder()
                .model(embeddingModel)
                .build()).build();
    }
}
