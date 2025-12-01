package be.talks.chatbots.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VoiceAssistantConfig {

    @Bean("voiceAssistantChatClient")
    public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
        ChatOptions chatOptions = ChatOptions.builder()
                .model("gpt-4.1-mini")
                .temperature(0.8)
                .build();

        return ChatClient.builder(openAiChatModel)
                .defaultOptions(chatOptions)
                .build();
    }
}
