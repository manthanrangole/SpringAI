package com.springAI.app.serviceImpl;

import com.springAI.app.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    private final OpenAiChatModel openAiChatModel;
    private final ChatClient chatClient;
    private final ImageModel imageModel;

    public OpenAiServiceImpl(OpenAiChatModel openAiChatModel, ImageModel imageModel) {
        this.openAiChatModel = openAiChatModel;
        this.chatClient = ChatClient.builder(openAiChatModel).build();
        this.imageModel = imageModel;
    }

    @Override
    public String getAnswer(String question) {
        logger.info("OpenAI Request received for question: {}", question);
        String response = chatClient.prompt().user(question).call().content();
        logger.info("OpenAI Response generated successfully");
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

    @Override
    public String generateImage(String description) {
        logger.info("Generating image with OpenAI for description: {}", description);
        ImageResponse response = imageModel.call(
                new ImagePrompt(description, 
                OpenAiImageOptions.builder()
                    .model("dall-e-3")
                    .height(1024)
                    .width(1024)
                    .build())
        );
        return response.getResult().getOutput().getUrl();
    }
}
