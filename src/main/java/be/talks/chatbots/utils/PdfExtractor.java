package be.talks.chatbots.utils;

import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class PdfExtractor {
    public String extractText(InputStream in) {
        // TODO: use Apache PDFBox, etc.
        return "PDF text extraction not implemented yet";
    }
}
