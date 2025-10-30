package be.talks.chatbots.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotCreationResponseDto {

    private String id;
    private String name;
    private String message;
    private String systemPrompt;
}
