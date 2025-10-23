package be.talks.chatbots.controller;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatModelController {

    private final OllamaChatModel chatModel;

    public ChatModelController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping
    public ResponseEntity<String> prompt(@RequestParam String message) {
        return ResponseEntity.ok(chatModel.call(message));
    }
}
