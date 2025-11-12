package be.talks.chatbots.usecase.service;

import be.talks.chatbots.adapter.controller.dto.BotCreationRequestDTO;
import be.talks.chatbots.adapter.controller.dto.BotCreationResponseDTO;
import be.talks.chatbots.adapter.repository.BotConfigEntity;
import be.talks.chatbots.adapter.repository.ChatBotRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class RagChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final ChatBotRepository chatBotRepository;

    public RagChatService(@Qualifier("ragChatClient") ChatClient chatClient, VectorStore vectorStore, ChatBotRepository chatBotRepository) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
        this.chatBotRepository = chatBotRepository;
    }

    public String randomChat(String message) {
        return chatClient.prompt()
                .user(message)
                .call().content();
    }

    public BotCreationResponseDTO createBot(BotCreationRequestDTO botCreationRequestDto) throws IOException {
        String botId = UUID.randomUUID().toString();

        extracted(botCreationRequestDto);

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

    private void extracted(BotCreationRequestDTO botCreationRequestDto) throws IOException {
        InputStreamResource resource = new InputStreamResource(botCreationRequestDto.getFile().getInputStream());
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> docs = tikaDocumentReader.get();
        /** Split the document in to chunks, this is needed for token usages. **/
        TextSplitter textSplitter =
                TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
        vectorStore.add(textSplitter.split(docs));
    }
}
