package be.talks.chatbots.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class OllamaConfig {

    @Primary
    @Bean
    public ChatModel primaryChatModel(OllamaChatModel ollamaChatModel) {
        return ollamaChatModel;
    }

    @Primary
    @Bean
    public OllamaEmbeddingModel ollamaEmbeddingModel() {
        var ollamaApi = OllamaApi.builder()
                .baseUrl("http://localhost:11434")
                .build();

        var options = OllamaOptions.builder().model("nomic-embed-text:latest").build();

        return OllamaEmbeddingModel.builder()
                .ollamaApi(ollamaApi)
                .defaultOptions(options)
                .build();
    }
}
