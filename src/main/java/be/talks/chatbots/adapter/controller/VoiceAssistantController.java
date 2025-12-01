package be.talks.chatbots.adapter.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/voice-assistant")
public class VoiceAssistantController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final SpeechModel speechModel;
    private final ChatClient chatClient;

    public VoiceAssistantController(OpenAiAudioTranscriptionModel transcriptionModel, SpeechModel speechModel, @Qualifier("voiceAssistantChatClient") ChatClient chatClient) {
        this.transcriptionModel = transcriptionModel;
        this.speechModel = speechModel;
        this.chatClient = chatClient;
    }

    /**
     * Complete voice assistant flow: Speech -> Text -> LLM -> Speech
     * Returns the audio file as bytes
     */
    @PostMapping(value = "/process", produces = "audio/mpeg")
    public ResponseEntity<byte[]> processVoiceInput(@RequestParam("file") MultipartFile audioFile) throws IOException {
        // Step 1: Speech-to-Text
        String transcribedText = transcriptionModel.call(audioFile.getResource());

        // Step 2: Process with LLM
        String llmResponse = chatClient.prompt().user(transcribedText).call().content();

        // Step 3: Text-to-Speech
        byte[] audioResponse = speechModel.call(llmResponse);

        // Return audio as response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
        headers.setContentDispositionFormData("attachment", "response.mp3");

        return new ResponseEntity<>(audioResponse, headers, HttpStatus.OK);
    }
}
