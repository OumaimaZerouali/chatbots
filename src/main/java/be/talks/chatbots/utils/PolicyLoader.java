package be.talks.chatbots.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PolicyLoader {

    private final VectorStore vectorStore;

    @Value("classpath:Garage_Appointment_Policy.pdf")
    Resource policyFile;

    public PolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPDF() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(policyFile);
        List<Document> docs = tikaDocumentReader.get();
        /** Split the document in to chunks, this is needed for token usages. **/
        TextSplitter textSplitter =
                TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
        vectorStore.add(textSplitter.split(docs));
    }
}
