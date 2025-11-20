package be.talks.chatbots.adapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatBotRepository extends JpaRepository<BotConfigEntity, Integer> {
    Optional<BotConfigEntity> findById(Integer id);
    List<BotConfigEntity> findByNameContainingIgnoreCase(String name);
}
