package be.talks.chatbots.utils;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class DocxExtractor {
    public String extractText(InputStream in) {
        // TODO: use Apache POI
        return "DOCX text extraction not implemented yet";
    }
}
