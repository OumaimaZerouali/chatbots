package be.talks.chatbots.adapter.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rag")
public class RAGChatController {

    private final ChatClient chatClient;

    public RAGChatController(@Qualifier("ragChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestParam("message") String message) {
        String answer = chatClient.prompt()
                .user(message)
                .call().content();
        return ResponseEntity.ok(answer);
    }
}
