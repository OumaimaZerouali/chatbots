package be.talks.chatbots.adapter.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/bot-config")
public class ChatHelperController {

    private final ChatClient chatClient;

    public ChatHelperController(@Qualifier("botConfigChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/search")
    public ResponseEntity<String> search(@RequestHeader("username") String username,
                                         @RequestParam("message") String message) {

        String answer = chatClient
                .prompt()
                .user(message)
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .call()
                .content();

        return ResponseEntity.ok(answer);
    }
}
