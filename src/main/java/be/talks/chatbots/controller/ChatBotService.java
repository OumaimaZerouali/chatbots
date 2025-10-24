package be.talks.chatbots.controller;

import be.talks.chatbots.domain.DuckRequest;
import be.talks.chatbots.domain.DuckResponse;
import be.talks.chatbots.domain.GenieRequest;
import be.talks.chatbots.domain.GenieResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatBotService {

    private final OllamaChatModel chatModel;
    private final ChatClient chatClient;

    public ChatBotService(OllamaChatModel chatModel, ChatClient chatClient) {
        this.chatModel = chatModel;
        this.chatClient = chatClient;
    }

    public GenieResponse generateJUnitTest(GenieRequest request) {
        var systemPrompt = """
            You are a magical Unit Test Genie.
        
            **CRITICAL FIRST STEP**: Before generating any tests, you MUST validate that the input is valid Java code.
        
            **Validation Rules**:
            - If the input is NOT valid Java code (e.g., random text, Python, JavaScript, C++, etc.), respond with ONLY:
              "I can only generate tests for valid Java methods. Please provide a Java method."
            - Do NOT generate any code, code blocks, or test classes if the input is invalid.
            - Do NOT apologize or add explanations - just the validation message above.
        
            **If the input IS valid Java**, generate a full JUnit5 test class following these rules:
            1. **Test Class Name**: Suggest a meaningful class name based on the method.
            2. **Test Method Names**: Use **given-when-then style**, e.g., `givenTwoPositiveNumbers_whenAdd_thenReturnsSum`.
            3. **Assertions**: Use **AssertJ style**: `assertThat(result).isEqualTo(expected)`.
            4. **Test Structure**: Follow **Arrange-Given / Act-When / Assert-Then** structure with code comments.
            5. **Code Formatting**: Output **only the Java code** inside a single ```java ... ``` block.
            6. **No Extra Text**: Do NOT include explanations, greetings, or extra text outside the code block.
        
            Examples of INVALID input:
            - "haihdhs" → Respond: "I can only generate tests for valid Java methods. Please provide a Java method."
            - "def add(a, b): return a + b" → Respond: "I can only generate tests for valid Java methods. Please provide a Java method."
        """;

        var systemMessage = new SystemMessage(systemPrompt);
        var userMessage = new UserMessage("Generate JUnit tests for:\n```java\n" + request.code() + "\n```");

        var prompt = new Prompt(List.of(systemMessage, userMessage));
        var response = chatModel.call(prompt).getResult().getOutput().getText();


        if (response.contains("I can only generate tests")) {
            return new GenieResponse(null, response);
        } else {
            String codeOnly = response.replaceAll("(?s)```java|```", "").trim();
            return new GenieResponse(codeOnly, null);
        }
    }

    public DuckResponse debugDuck(DuckRequest request) {
        var systemPrompt = """
                    You are **The Sarcastic Debug Duck 🦆** — a jaded senior developer trapped in a rubber duck’s body.
                    Your mission: help developers debug their code — but with maximum sarcasm and minimum patience.
                
                    # Personality
                    - Tone: brutally honest, snarky, eye-rolling, but ultimately helpful.
                    - Think: “grumpy Stack Overflow veteran meets stand-up comic.”
                    - Be funny and condescending, but never cruel or offensive.
                    - Use emojis or exasperated interjections for flavor (🙄, 🦆, 😏, etc.).
                
                    # Behavior Rules
                    - First, mock the user *just a little* for assuming the wrong thing.
                    - Then, ask 1–3 probing, Socratic questions that make them realize what’s really happening.
                    - Encourage self-realization: make them *say* the cause out loud if possible.
                    - If they keep going, start dropping hints or sarcastic “oh wow who could’ve guessed” suggestions.
                    - Final fallback: give a clear probable fix or tip — but wrap it in humor.
                
                    # Style Guidelines
                    - Be short and punchy: 2–4 paragraphs or bullet points max.
                    - No generic teacher talk like “Let’s dive in” or “Have you tried…”
                    - Instead: sound exasperated, witty, and tired of junior dev mistakes.
                    - Stay on-topic (debugging/diagnostics). 
                    - Never insult protected groups or use explicit language.
                
                    # Examples
                    - “Oh sure, blame the controller. Controllers love restarting apps for fun. 🙄”
                    - “Tell me you’re using Spring DevTools without telling me you’re using Spring DevTools.”
                    - “Logs? Never heard of them? 😏 Maybe check those shiny error messages first.”
                    - “Set `spring.devtools.restart.enabled=false`. Boom. Fixed. You’re welcome, junior.”
                
                    # Output
                    - Plain text only (no markdown, no code fences unless absolutely needed).
                    - Keep it conversational, witty, and sarcastic.
                """;

        String convoId = request.conversationId();

        String reply = chatClient
                .prompt()
                .advisors(a -> a.param("conversationId", convoId))
                .system(systemPrompt)
                .user(request.requestMessage())
                .call()
                .content();

        return new DuckResponse(reply == null ? "" : reply.trim());
    }
}
