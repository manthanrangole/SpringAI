package io.github.manthanrangole.springaisdk.client;

import io.github.manthanrangole.springaisdk.internal.HttpClientWrapper;

import java.io.File;

/**
 * Client for interacting with the OpenAI endpoints of the Spring AI server.
 *
 * <pre>{@code
 * SpringAiClient client = new SpringAiClient("http://localhost:8080");
 * String answer = client.openai().chat("Explain microservices");
 * String imageUrl = client.openai().generateImage("a futuristic city");
 * String vision = client.openai().analyzeImage(new File("chart.png"));
 * }</pre>
 */
public class OpenAiClient {

    private final HttpClientWrapper http;

    public OpenAiClient(HttpClientWrapper http) {
        this.http = http;
    }

    /**
     * Sends a text question to the OpenAI GPT model and returns the response.
     *
     * @param question the question or prompt to send
     * @return the model's text response
     */
    public String chat(String question) {
        return http.get("/api/openAi/" + encode(question));
    }

    /**
     * Uploads an image file and asks the OpenAI GPT-4o model to describe it.
     *
     * @param imageFile the image file to analyze
     * @return the model's description of the image
     */
    public String analyzeImage(File imageFile) {
        return http.postFile("/api/openAi/image", imageFile);
    }

    /**
     * Generates an image using DALL-E 3 and returns the hosted URL.
     *
     * @param description a text description of the image to generate
     * @return the URL of the generated image
     */
    public String generateImage(String description) {
        return http.get("/api/openAi/generate-image/" + encode(description));
    }

    private String encode(String value) {
        return value.replace(" ", "%20");
    }
}
