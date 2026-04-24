package io.github.manthanrangole.springaisdk.client;

import io.github.manthanrangole.springaisdk.internal.HttpClientWrapper;

import java.io.File;

/**
 * Client for interacting with the Anthropic (Claude) endpoints of the Spring AI server.
 *
 * <pre>{@code
 * SpringAiClient client = new SpringAiClient("http://localhost:8080");
 * String answer = client.anthropic().chat("What is Java?");
 * String imageUrl = client.anthropic().generateImage("a sunset over mountains");
 * String vision = client.anthropic().analyzeImage(new File("diagram.png"));
 * }</pre>
 */
public class AnthropicClient {

    private final HttpClientWrapper http;

    public AnthropicClient(HttpClientWrapper http) {
        this.http = http;
    }

    /**
     * Sends a text question to the Anthropic Claude model and returns the response.
     *
     * @param question the question or prompt to send
     * @return the model's text response
     */
    public String chat(String question) {
        return http.get("/api/anthropic/" + encode(question));
    }

    /**
     * Uploads an image file and asks the Anthropic Claude model to describe it.
     *
     * @param imageFile the image file to analyze
     * @return the model's description of the image
     */
    public String analyzeImage(File imageFile) {
        return http.postFile("/api/anthropic/image", imageFile);
    }

    /**
     * Generates an image using DALL-E 3 via Anthropic endpoint and returns the hosted URL.
     *
     * @param description a text description of the image to generate
     * @return the URL of the generated image
     */
    public String generateImage(String description) {
        return http.get("/api/anthropic/generate-image/" + encode(description));
    }

    private String encode(String value) {
        return value.replace(" ", "%20");
    }
}
