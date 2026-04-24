package com.springAI.app.serviceImpl;

import com.springAI.app.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@Service
public class OllamaServiceImpl implements OllamaService {

    private static final Logger logger = LoggerFactory.getLogger(OllamaServiceImpl.class);

    private final OllamaChatModel ollamaChatModel;
    private final ChatClient chatClient;

    public OllamaServiceImpl(OllamaChatModel ollamaChatModel) {
        this.ollamaChatModel = ollamaChatModel;
        this.chatClient = ChatClient.builder(ollamaChatModel).build();
    }

    @Override
    public String getAnswer(String question) {
        logger.info("Ollama Request received for question: {}", question);
        String response = chatClient.prompt().user(question).call().content();
        logger.info("Ollama Response generated successfully");
        return response;
    }

    public String getAnswerWithSystemPrompt(String question) {
        return chatClient.prompt()
                .system("You are a sarcastic but helpful assistant. Keep answers under 2 sentences.")
                .user(question)
                .call()
                .content();
    }

    public record MovieInfo(String title, String director, int releaseYear) {
    }

    public MovieInfo getMovieInfo(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .entity(MovieInfo.class);
    }

    public Flux<String> getStreamedAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }

    @Override
    public String getAnswerWithImage(MultipartFile file) {
        try {
            return chatClient.prompt()
                    .user(u -> u.text("Explain what is in this image")
                            .media(MimeTypeUtils.parseMimeType(file.getContentType()), file.getResource()))
                    .call()
                    .content();
        } catch (Exception e) {
            throw new RuntimeException("Failed to process image", e);
        }
    }
}
