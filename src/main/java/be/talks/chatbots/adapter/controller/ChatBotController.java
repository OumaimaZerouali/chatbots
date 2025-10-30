package be.talks.chatbots.adapter.controller;

import be.talks.chatbots.domain.DuckRequest;
import be.talks.chatbots.domain.DuckResponse;
import be.talks.chatbots.domain.GenieRequest;
import be.talks.chatbots.domain.GenieResponse;
import be.talks.chatbots.adapter.controller.dto.BotCreationRequestDto;
import be.talks.chatbots.adapter.controller.dto.BotCreationResponseDto;
import be.talks.chatbots.adapter.controller.dto.ChatRequestDto;
import be.talks.chatbots.adapter.controller.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/bot-factory")
    public ResponseEntity<BotCreationResponseDto> createBot(@RequestBody BotCreationRequestDto botCreationRequestDto) {
        BotCreationResponseDto responseDto = chatBotService.createBot(botCreationRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDto> chat(@RequestBody ChatRequestDto chatRequestDto) {
        ChatResponseDto responseDto = chatBotService.chat(chatRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
