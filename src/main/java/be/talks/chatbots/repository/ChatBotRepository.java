package be.talks.chatbots.repository;

import be.talks.chatbots.domain.BotConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatBotRepository extends JpaRepository<BotConfig, Integer> {
    Optional<BotConfig> findById(Integer id);
}
