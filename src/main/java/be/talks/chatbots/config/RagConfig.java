package be.talks.chatbots.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RagConfig {

    @Bean("ragChatClient")
    public ChatClient chatClient(ChatClient.Builder clientBuilder,
                                 RetrievalAugmentationAdvisor retrievalAugmentationAdvisor,
                                 @Qualifier("ragChatMemory") ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        var memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory)
                .build();

        return clientBuilder
                .defaultAdvisors(List.of(loggerAdvisor,
                        retrievalAugmentationAdvisor,
                        memoryAdvisor))
                .build();
    }

    /**
     * This bean will retrieve the important documents from the vector store.
     **/
    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(3).similarityThreshold(0.5).build())
                .build();
    }

    /**
     * This bean provide a chat memory for the model.
     **/
    @Bean("ragChatMemory")
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(30)
                .build();
    }
}
