package be.talks.chatbots.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;

@Component
public class PromptLoader {

    public String load(String path) {
        try {
            var resource = new ClassPathResource(path);
            return Files.readString(resource.getFile().toPath());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load prompt: " + path, e);
        }
    }
}

