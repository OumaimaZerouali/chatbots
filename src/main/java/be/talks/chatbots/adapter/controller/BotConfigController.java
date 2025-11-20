package be.talks.chatbots.adapter.controller;

import be.talks.chatbots.adapter.repository.BotConfigEntity;
import be.talks.chatbots.adapter.repository.ChatBotRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bot-configs")
public class BotConfigController {

    private final ChatBotRepository chatBotRepository;

    public BotConfigController(ChatBotRepository chatBotRepository) {
        this.chatBotRepository = chatBotRepository;
    }

    @GetMapping
    public List<BotConfigEntity> getAllBotConfigs() {
        return chatBotRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BotConfigEntity> getBotConfigById(@PathVariable Integer id) {
        return chatBotRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public List<BotConfigEntity> getBotConfigsByName(@RequestParam String name) {
        return chatBotRepository.findByNameContainingIgnoreCase(name);
    }
}
