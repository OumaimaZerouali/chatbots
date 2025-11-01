package be.talks.chatbots.adapter.controller;

import be.talks.chatbots.adapter.controller.dto.BotCreationRequestDTO;
import be.talks.chatbots.adapter.controller.dto.BotCreationResponseDTO;
import be.talks.chatbots.adapter.controller.dto.ChatRequestDTO;
import be.talks.chatbots.adapter.controller.dto.ChatResponseDTO;
import be.talks.chatbots.domain.DuckRequest;
import be.talks.chatbots.domain.DuckResponse;
import be.talks.chatbots.domain.GenieRequest;
import be.talks.chatbots.domain.GenieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/genie/test")
    public ResponseEntity<GenieResponse> generateTest(@RequestBody GenieRequest request) {
        if (request.code() == null || request.code().isBlank()) {
            return ResponseEntity.badRequest().body(new GenieResponse("Please provide a Java method.", null));
        }
        return ResponseEntity.ok(chatBotService.generateJUnitTest(request));
    }

    @PostMapping("/duck/debug")
    public ResponseEntity<DuckResponse> debug(@RequestBody DuckRequest request) {
        DuckResponse response = chatBotService.debugDuck(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/bot-factory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BotCreationResponseDTO> createBot(@ModelAttribute BotCreationRequestDTO botCreationRequestDto) {
        BotCreationResponseDTO responseDto = chatBotService.createBot(botCreationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDTO> chat(@RequestBody ChatRequestDTO chatRequestDto) {
        ChatResponseDTO responseDto = chatBotService.chat(chatRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/bots")
    public ResponseEntity<List<BotCreationResponseDTO>> getAllBots() {
        List<BotCreationResponseDTO> bots = chatBotService.getAllBots();
        return ResponseEntity.ok(bots);
    }
}
