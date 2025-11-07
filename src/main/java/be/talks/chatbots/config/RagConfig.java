package be.talks.chatbots.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RagConfig {

    @Bean("ragChatClient")
    public ChatClient chatClient(ChatClient.Builder clientBuilder, RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        return clientBuilder
                .defaultAdvisors(List.of(loggerAdvisor, retrievalAugmentationAdvisor))
                .build();
    }

    /** This bean will retrieve the important documents from the vector store. **/
    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore) {
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder().vectorStore(vectorStore)
                        .topK(3).similarityThreshold(0.5).build())
                .build();
    }
}
