package be.talks.chatbots.usecase.service;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class FileProcessingService {

//    public String processFiles(MultipartFile file) {
//        try {
//            String filename = file.getOriginalFilename();
//            if (filename == null) {
//                filename = "uploaded";
//            }
//
//            String text;
//            if (filename.toLowerCase().endsWith(".pdf")) {
//                text = extractPdfText(file.getInputStream());
//            } else if (filename.toLowerCase().endsWith(".txt")) {
//                text = extractTxt(file.getInputStream());
//            } else {
//                return "{\"status\":\"error\",\"message\":\"Only .txt or .pdf allowed\"}";
//            }
//
//            // Very naive guard: don't index empty/noisy uploads
//            if (text == null || text.isBlank()) {
//                return "{\"status\":\"error\",\"message\":\"File had no readable text\"}";
//            }
//
//            return text;
//        } catch (Exception e) {
//            return "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
//        }
//    }
//
//    private String extractTxt(InputStream in) throws Exception {
//        return IOUtils.toString(in, StandardCharsets.UTF_8);
//    }
//
//    private String extractPdfText(InputStream in) throws Exception {
//        try (PDDocument pdf = PDDocument.load(in)) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            return stripper.getText(pdf);
//        }
//    }
}
