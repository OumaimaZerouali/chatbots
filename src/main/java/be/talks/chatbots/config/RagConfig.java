package be.talks.chatbots.config;


import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Creates the VectorStore bean backed by Ollama embeddings.
 * This store will live in memory for the life of the app.
 */
@Configuration
public class RagConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        // in-memory vector store using Ollama's embedding model
        return SimpleVectorStore.builder(embeddingModel).build();
    }
}
