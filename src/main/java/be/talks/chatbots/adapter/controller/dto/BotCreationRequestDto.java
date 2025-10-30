package be.talks.chatbots.adapter.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotCreationRequestDto {

    private String name;
    private String personality;
    private String purpose;
    private String restrictions;
    private List<MultipartFile> files;
}
