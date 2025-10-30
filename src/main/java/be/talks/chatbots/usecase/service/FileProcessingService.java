package be.talks.chatbots.usecase.service;

import be.talks.chatbots.domain.ProcessedFile;
import be.talks.chatbots.utils.CodeParser;
import be.talks.chatbots.utils.CsvParser;
import be.talks.chatbots.utils.DocxExtractor;
import be.talks.chatbots.utils.PdfExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileProcessingService {

    private final PdfExtractor pdfExtractor;
    private final DocxExtractor docxExtractor;
    private final CodeParser codeParser;
    private final CsvParser csvParser;

    public List<ProcessedFile> processFiles(List<MultipartFile> files, Integer botId) {
        List<ProcessedFile> processed = new ArrayList<>();

        if (files == null || files.isEmpty()) {
            return processed;
        }

        for (MultipartFile file : files) {
            try {
                String content = extractContent(file);

                ProcessedFile pf = ProcessedFile.builder()
                        .id(UUID.randomUUID().toString())
                        .botId(botId)
                        .originalFileName(file.getOriginalFilename())
                        .type(detectFileType(file))
                        .content(content)
                        .size(file.getSize())
                        .uploadedAt(Instant.now())
                        .build();

                processed.add(pf);

            } catch (Exception e) {
                log.error("Failed to process file: {}", file.getOriginalFilename(), e);
            }
        }

        return processed;
    }

    private String extractContent(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();

        if (filename == null) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        String lower = filename.toLowerCase();

        if (lower.endsWith(".txt") || lower.endsWith(".md")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        if (lower.endsWith(".pdf")) {
            return pdfExtractor.extractText(file.getInputStream());
        }

        if (lower.endsWith(".docx")) {
            return docxExtractor.extractText(file.getInputStream());
        }

        if (lower.endsWith(".java")) {
            String code = new String(file.getBytes(), StandardCharsets.UTF_8);
            // Parse and add metadata
            return codeParser.parseJava(code);
        }

        if (lower.endsWith(".json")) {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
        }

        if (lower.endsWith(".csv")) {
            return csvParser.parseToText(file.getInputStream());
        }

        // Default: try to read as text
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }

    private String detectFileType(MultipartFile file) {
        String name = file.getOriginalFilename();
        if (name == null) {
            return "unknown";
        }
        int dot = name.lastIndexOf('.');
        if (dot == -1) {
            return "unknown";
        }
        return name.substring(dot + 1).toLowerCase(); // "pdf", "txt", etc.
    }
}
