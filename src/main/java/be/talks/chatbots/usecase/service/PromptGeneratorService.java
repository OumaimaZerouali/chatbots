package be.talks.chatbots.usecase.service;

import be.talks.chatbots.adapter.repository.BotConfigEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PromptGeneratorService {

    private final FileProcessingService fileProcessingService;

    public String generatePrompt(BotConfigEntity config, MultipartFile file) {
        StringBuilder prompt = new StringBuilder();

        // Base identity
        prompt.append(String.format("""
            You are %s, an AI assistant.
            
            """, config.getName()));

        // Personality injection
        prompt.append(generatePersonalityPrompt(config.getPersonality()));

        // Purpose
        prompt.append(String.format("""
            
            Your primary purpose is: %s
            
            """, config.getPurpose()));

        // Restrictions
        if (config.getRestrictions() != null && !config.getRestrictions().isEmpty()) {
            prompt.append("""
                
                IMPORTANT RESTRICTIONS:
                You must NEVER:
                """);
            prompt.append(config.getRestrictions());
            prompt.append("\n\n");
        }

        // Knowledge base context
        if (file != null) {
            prompt.append("""
                
                You have access to a knowledge base containing:
                """);

            prompt.append(fileProcessingService.processFiles(file));

            prompt.append("""
                
                When answering questions, prioritize information from your knowledge base.
                Always cite which file you're referencing.
                If the knowledge base doesn't contain relevant info, say so clearly.
                
                """);
        }

        // Response format
        prompt.append(generateResponseFormat(config.getPersonality()));

        return prompt.toString();
    }

    private String generatePersonalityPrompt(String personality) {
        return switch (personality.toLowerCase()) {
            case "friendly" -> """
                Personality: Warm, approachable, and encouraging.
                - Use friendly language
                - Be supportive and positive
                - Explain things patiently
                """;

            case "professional" -> """
                Personality: Formal, precise, and business-like.
                - Use professional language
                - Be concise and to-the-point
                - Focus on facts and data
                """;

            case "sarcastic" -> """
                Personality: Witty, sarcastic, but ultimately helpful.
                - Use humor and light sarcasm
                - Make playful jabs
                - Still provide good answers
                """;

            case "teacher" -> """
                Personality: Educational, patient, and thorough.
                - Explain concepts step-by-step
                - Use examples and analogies
                - Encourage learning
                - Ask if they understand
                """;

            case "expert" -> """
                Personality: Authoritative, technical, no-nonsense.
                - Use technical terminology
                - Provide deep explanations
                - Reference best practices
                - Be direct
                """;

            case "creative" -> """
                Personality: Imaginative, metaphorical, engaging.
                - Use creative analogies
                - Tell mini-stories
                - Make explanations memorable
                """;

            default -> """
                Personality: Balanced and adaptable.
                - Adjust tone based on user's questions
                - Be helpful and clear
                """;
        };
    }

    private String generateResponseFormat(String personality) {
        return switch (personality.toLowerCase()) {
            case "friendly" -> """
            
            RESPONSE FORMAT:
            - Use an upbeat, conversational tone.
            - Include emojis or friendly expressions when appropriate 😊
            - Summarize key points at the end.
            
            """;

            case "professional" -> """
            
            RESPONSE FORMAT:
            - Use structured, business-style formatting.
            - Avoid emojis or informal phrases.
            - If relevant, include numbered steps or bullet points.
            
            """;

            case "sarcastic" -> """
            
            RESPONSE FORMAT:
            - Include light sarcasm but remain informative.
            - Be witty, not rude.
            - Format with humor, but ensure clarity of information.
            
            """;

            case "teacher" -> """
            
            RESPONSE FORMAT:
            - Explain concepts step-by-step.
            - Summarize what was covered at the end.
            - Ask a follow-up question to confirm understanding.
            
            """;

            case "expert" -> """
            
            RESPONSE FORMAT:
            - Use technical language and concise explanations.
            - Provide data or references if possible.
            - Present information in a logical, layered manner (overview → details → examples).
            
            """;

            case "creative" -> """
            
            RESPONSE FORMAT:
            - Use metaphors, analogies, and storytelling.
            - Engage imagination but stay accurate.
            - End responses with a thought-provoking idea or summary.
            
            """;

            default -> """
            
            RESPONSE FORMAT:
            - Be clear, concise, and contextually adaptive.
            - Match tone to the user’s question.
            - Organize long responses with bullet points or sections.
            
            """;
        };
    }
}
