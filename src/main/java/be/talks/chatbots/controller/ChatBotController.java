package be.talks.chatbots.controller;

import be.talks.chatbots.domain.DuckRequest;
import be.talks.chatbots.domain.DuckResponse;
import be.talks.chatbots.domain.GenieRequest;
import be.talks.chatbots.domain.GenieResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatBotController {
    private final ChatBotService chatBotService;

    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

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
}
