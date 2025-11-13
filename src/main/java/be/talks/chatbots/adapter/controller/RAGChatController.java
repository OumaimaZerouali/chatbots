package be.talks.chatbots.adapter.controller;

import be.talks.chatbots.adapter.controller.dto.BotCreationRequestDTO;
import be.talks.chatbots.adapter.controller.dto.BotCreationResponseDTO;
import be.talks.chatbots.adapter.controller.dto.ChatRequestDTO;
import be.talks.chatbots.adapter.controller.dto.ChatResponseDTO;
import be.talks.chatbots.usecase.service.RagChatService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/rag")
public class RAGChatController {

    private final RagChatService ragChatService;

    public RAGChatController(RagChatService ragChatService) {
        this.ragChatService = ragChatService;
    }

    @PostMapping(value = "/bot-factory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BotCreationResponseDTO> createBot(@ModelAttribute BotCreationRequestDTO botCreationRequestDto) throws IOException {
        var response = ragChatService.createBot(botCreationRequestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO chatRequestDto) {
        var response = ragChatService.chat(chatRequestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bots")
    public ResponseEntity<List<BotCreationResponseDTO>> getAllBots() {
        var response = ragChatService.getAllBots();
        return ResponseEntity.ok(response);
    }
}
