package be.talks.chatbots.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rag")
public class RAGChatController {

    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore;

    private final String promptTemplate = """
            You are a helpful support assistant.
            You MUST answer ONLY using the context below.
            If the answer is not in the context, reply exactly:
            "I don't know from the docs."
            
            CONTEXT:
            %s
            
            QUESTION:
            %s
            """;

    @PostMapping(
            path = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            if (filename == null) {
                filename = "uploaded";
            }

            String text;
            if (filename.toLowerCase().endsWith(".pdf")) {
                text = extractPdfText(file.getInputStream());
            } else if (filename.toLowerCase().endsWith(".txt")) {
                text = extractTxt(file.getInputStream());
            } else {
                return "{\"status\":\"error\",\"message\":\"Only .txt or .pdf allowed\"}";
            }

            // Very naive guard: don't index empty/noisy uploads
            if (text == null || text.isBlank()) {
                return "{\"status\":\"error\",\"message\":\"File had no readable text\"}";
            }

            // Wrap it in a Spring AI Document
            Document doc = new Document(text);
            // Add it to the vector store -> this will create embeddings via Ollama
            vectorStore.add(List.of(doc));

            return "{\"status\":\"ok\",\"indexedChars\":" + text.length() + "}";
        } catch (Exception e) {
            return "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
        }
    }

    @GetMapping(
            path = "/ask",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String ask(@RequestParam("query") String query) {

        // 1. semantic retrieve top K matches from vector store
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(4)
                .build();

        var matches = vectorStore.similaritySearch(request);

        // 2. merge relevant docs' text
        String contextBlock = matches.stream()
                .map(doc -> doc.getText())
                .reduce((a, b) -> a + "\n---\n" + b)
                .orElse("No relevant context found.");

        // 3. build prompt for the model
        String finalPrompt = String.format(promptTemplate, contextBlock, query);

        // 4. ask Ollama model via ChatClient
        ChatClient chatClient = chatClientBuilder.build();
        String answer = chatClient
                .prompt()
                .user(finalPrompt)
                .call()
                .content();

        // Return JSON-ish string
        return "{\"answer\":" + quoteJson(answer) + "}";
    }

    private String extractTxt(InputStream in) throws Exception {
        return IOUtils.toString(in, StandardCharsets.UTF_8);
    }

    private String extractPdfText(InputStream in) throws Exception {
        try (PDDocument pdf = PDDocument.load(in)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(pdf);
        }
    }

    // minimal JSON string escaper so we don't break quotes in {"answer":"..."}
    private String quoteJson(String raw) {
        if (raw == null) return "\"\"";
        String escaped = raw
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n");
        return "\"" + escaped + "\"";
    }
}
