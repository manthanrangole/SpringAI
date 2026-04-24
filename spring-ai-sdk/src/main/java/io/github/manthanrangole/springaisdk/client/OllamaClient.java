package io.github.manthanrangole.springaisdk.client;

import io.github.manthanrangole.springaisdk.internal.HttpClientWrapper;

import java.io.File;

/**
 * Client for interacting with the Ollama (local model) endpoints of the Spring AI server.
 *
 * <pre>{@code
 * SpringAiClient client = new SpringAiClient("http://localhost:8080");
 * String answer = client.ollama().chat("What is Docker?");
 * String vision = client.ollama().analyzeImage(new File("diagram.png"));
 * }</pre>
 */
public class OllamaClient {

    private final HttpClientWrapper http;

    public OllamaClient(HttpClientWrapper http) {
        this.http = http;
    }

    /**
     * Sends a text question to the locally running Ollama model and returns the response.
     *
     * @param question the question or prompt to send
     * @return the model's text response
     */
    public String chat(String question) {
        return http.get("/api/ollama/" + encode(question));
    }

    /**
     * Uploads an image file and asks the Ollama model to describe it.
     *
     * @param imageFile the image file to analyze
     * @return the model's description of the image
     */
    public String analyzeImage(File imageFile) {
        return http.postFile("/api/ollama/image", imageFile);
    }

    /**
     * Note: Ollama does not natively support image generation.
     * This method returns an informational message.
     *
     * @param description a text description
     * @return informational message about Ollama image generation limitations
     */
    public String generateImage(String description) {
        return http.get("/api/ollama/generate-image/" + encode(description));
    }

    private String encode(String value) {
        return value.replace(" ", "%20");
    }
}
