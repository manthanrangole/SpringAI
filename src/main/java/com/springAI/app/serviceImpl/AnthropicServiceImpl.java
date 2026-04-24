package com.springAI.app.serviceImpl;

import com.springAI.app.service.AnthropicService;

import reactor.core.publisher.Flux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AnthropicServiceImpl implements AnthropicService {

    private static final Logger logger = LoggerFactory.getLogger(AnthropicServiceImpl.class);

    private final AnthropicChatModel anthropicChatModel;

    // With ChatClient
    private final ChatClient chatClient;

    private ImageModel imageModel;

    public AnthropicServiceImpl(AnthropicChatModel anthropicChatModel, ImageModel imageModel) {
        this.anthropicChatModel = anthropicChatModel;
        this.chatClient = ChatClient.builder(anthropicChatModel).build();
        this.imageModel = imageModel;
    }

    @Override
    public String getAnswer(String question) {
        logger.info("Anthropic Request received for question: {}", question);

        // With AnthropicChatModel
        // String response = anthropicChatModel.(question);

        // With ChatClient
        String response = chatClient.prompt().user(question).call().content();
        logger.info("Anthropic Response generated successfully");
        return response;
    }

    public String getAnswerWithSystemPrompt(String question) {
        return chatClient.prompt()
                .system("You are a sarcastic but helpful assistant. Keep answers under 2 sentences.")
                .user(question)
                .call()
                .content();
    }

    // Define a record
    public record MovieInfo(String title, String director, int releaseYear) {
    }

    public MovieInfo getMovieInfo(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .entity(MovieInfo.class); // Automatically maps AI response to Java Object!
    }

    // If the response is long, you can use .stream() to send chunks of the response
    // back to your client as soon as they are generated (like ChatGPT's typing
    // effect) using Spring WebFlux.

    public Flux<String> getStreamedAnswer(String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content(); // Returns a Flux<String>
    }

    // f you use a multimodal model (like Claude 3 or GPT-4o), you can pass images
    // inside your prompt.
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
                                .build()));
        return response.getResult().getOutput().getUrl();
    }

}
