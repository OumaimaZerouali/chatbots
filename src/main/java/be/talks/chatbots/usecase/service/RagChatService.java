package be.talks.chatbots.usecase.service;

import be.talks.chatbots.adapter.controller.dto.BotCreationRequestDTO;
import be.talks.chatbots.adapter.controller.dto.BotCreationResponseDTO;
import be.talks.chatbots.adapter.controller.dto.ChatRequestDTO;
import be.talks.chatbots.adapter.controller.dto.ChatResponseDTO;
import be.talks.chatbots.adapter.repository.BotConfigEntity;
import be.talks.chatbots.adapter.repository.ChatBotRepository;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RagChatService {

    private final ChatClient chatClient;
    private final ChatBotRepository chatBotRepository;
    private final QdrantClient qdrantClient;
    private final EmbeddingModel embeddingModel;

    public RagChatService(ChatClient chatClient, ChatBotRepository chatBotRepository, QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
        this.chatClient = chatClient;
        this.chatBotRepository = chatBotRepository;
        this.qdrantClient = qdrantClient;
        this.embeddingModel = embeddingModel;
    }

    public BotCreationResponseDTO createBot(BotCreationRequestDTO botCreationRequestDto) throws IOException {
        String botId = UUID.randomUUID().toString();

        createQdrantCollection(botCreationRequestDto.getName());
        storeDocumentsInCollection(botCreationRequestDto);

        BotConfigEntity config = BotConfigEntity.builder()
                .configId(botId)
                .name(botCreationRequestDto.getName())
                .personality(botCreationRequestDto.getPersonality())
                .purpose(botCreationRequestDto.getPurpose())
                .restrictions(botCreationRequestDto.getRestrictions())
                .build();

        chatBotRepository.save(config);

        return BotCreationResponseDTO.builder()
                .id(botId)
                .name(botCreationRequestDto.getName())
                .message("Bot created successfully! Start chatting!")
                .build();
    }

    public ChatResponseDTO chat(ChatRequestDTO chatRequestDto) {
        String convoId = chatRequestDto.getConversationId();

        // Fetch bot configuration
        BotConfigEntity botConfig = chatBotRepository.findBotConfigEntitiesByName(chatRequestDto.getCollectionName())
                .orElseThrow(() -> new RuntimeException("Bot not found"));

        // Build system prompt
        String systemPrompt = buildSystemPrompt(
                botConfig.getPersonality(),
                botConfig.getPurpose(),
                botConfig.getRestrictions()
        );

        // Create a VectorStore instance for the specific collection
        QdrantVectorStore vectorStore = QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName(chatRequestDto.getCollectionName())
                .initializeSchema(false)
                .build();

        // Search for similar documents in the collection
        List<Document> similarDocuments = vectorStore.similaritySearch(chatRequestDto.getQuestion());

        // Build context from retrieved documents
        String context = similarDocuments.stream()
                .map(Document::getFormattedContent)
                .reduce("", (acc, content) -> acc + "\n" + content);

        // Use ChatClient to generate answer based on context
        String answer = chatClient.prompt()
                .system(systemPrompt)
                .advisors(a -> a.param("conversationId", convoId))
                .user(userSpec -> userSpec
                        .text("""
                                Based on the following context, answer the question.
                                If the answer cannot be found in the context, say so.
                                
                                Context: {context}
                                
                                Question: {question}
                                
                                Answer the question using only the context provided. If you cannot answer based on the context, say "Sorry, but I don't have information about that topic."
                                """)
                        .param("context", context)
                        .param("question", chatRequestDto.getQuestion()))
                .call()
                .content();

        return ChatResponseDTO.builder()
                .message(answer == null ? "" : answer.trim())
                .build();
    }

    private String buildSystemPrompt(String personality, String purpose, String restrictions) {
        StringBuilder prompt = new StringBuilder();

        if (personality != null && !personality.isBlank()) {
            prompt.append("Personality: ").append(personality).append("\n\n");
        }

        if (purpose != null && !purpose.isBlank()) {
            prompt.append("Purpose: ").append(purpose).append("\n\n");
        }

        if (restrictions != null && !restrictions.isBlank()) {
            prompt.append("Restrictions: ").append(restrictions);
        }

        prompt.append("""
            Important Instructions:
            - Answer questions directly and concisely based on the provided context
            - If the information is not in the context, simply say "Sorry, but I don't have information about that topic"
            - Do not analyze or explain what is or isn't in the context
            - Keep responses brief and natural
            """);

        return prompt.toString().trim();
    }

    public List<BotCreationResponseDTO> getAllBots() {
        return chatBotRepository.findAll().stream()
                .map(bot -> BotCreationResponseDTO.builder()
                        .id(String.valueOf(bot.getId()))
                        .name(bot.getName())
                        .systemPrompt(bot.getSystemPrompt())
                        .build())
                .toList();
    }

    public void createQdrantCollection(String collectionName) {
        try {
            qdrantClient.createCollectionAsync(
                    collectionName,
                    Collections.VectorParams.newBuilder()
                            .setSize(768)
                            .setDistance(Collections.Distance.Cosine)
                            .build()
            ).get();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Qdrant collection: " + collectionName, e);
        }
    }

    public void storeDocumentsInCollection(BotCreationRequestDTO botCreationRequestDTO) throws IOException {
        // Read the uploaded file
        InputStreamResource resource = new InputStreamResource(botCreationRequestDTO.getFile().getInputStream());
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> docs = tikaDocumentReader.get();

        // Split documents into chunks
        TextSplitter textSplitter = TokenTextSplitter.builder()
                .withChunkSize(100)
                .withMaxNumChunks(400)
                .build();
        List<Document> chunks = textSplitter.split(docs);

        // Create a VectorStore instance for this specific collection
        QdrantVectorStore botVectorStore = QdrantVectorStore.builder(qdrantClient, embeddingModel)
                .collectionName(botCreationRequestDTO.getName())
                .initializeSchema(true)
                .build();

        // Store documents in the bot-specific collection
        botVectorStore.add(chunks);
    }
}
