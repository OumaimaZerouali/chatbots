package be.talks.chatbots.adapter.repository;

import be.talks.chatbots.domain.ProcessedFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;
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
@Entity
public class BotConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String configId;
    private String name;
    private String personality;
    private String purpose;
    private String restrictions;
    @Lob
    @Column(columnDefinition = "CLOB")
    private String systemPrompt;
    @Transient
    private List<MultipartFile> files;
    @Transient
    private List<ProcessedFile> processedFiles;
}
