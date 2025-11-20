package be.talks.chatbots.tools;

import be.talks.chatbots.adapter.repository.BotConfigEntity;
import be.talks.chatbots.adapter.repository.ChatBotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotConfigurationTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotConfigurationTools.class);
    private final ChatBotRepository chatBotRepository;

    public BotConfigurationTools(ChatBotRepository chatBotRepository) {
        this.chatBotRepository = chatBotRepository;
    }

    @Tool(name = "getAllBotConfigs", description = "Get all bots configs available.")
    List<BotConfigEntity> getAllBotConfigs() {
        LOGGER.info("Getting all bots configs");
        return chatBotRepository.findAll();
    }

    @Tool(name = "getBotConfigById", description = "Get bot config by id.")
    BotConfigEntity getBotConfigById(@ToolParam(description = "Value representing the bot config id") Integer id) {
        LOGGER.info("Getting bots config by id {}", id);
        return chatBotRepository.findById(id).orElse(null);
    }

    @Tool(name = "getBotConfigsByName", description = "Get all bots configs by name.")
    List<BotConfigEntity> getBotConfigsByName(@ToolParam(description = "Value representing the bot config name") String name) {
        LOGGER.info("Getting bots configs by name {}", name);
        return chatBotRepository.findByNameContainingIgnoreCase(name);
    }
}
