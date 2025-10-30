package be.talks.chatbots.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessedFile {

    private String id;
    private Integer botId;
    private String originalFileName;
    private String type;
    private String content;
    private long size;
    private Instant uploadedAt;
}
