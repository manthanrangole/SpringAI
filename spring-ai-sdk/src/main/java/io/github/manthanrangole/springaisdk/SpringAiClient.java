package io.github.manthanrangole.springaisdk;

import io.github.manthanrangole.springaisdk.client.AnthropicClient;
import io.github.manthanrangole.springaisdk.client.OllamaClient;
import io.github.manthanrangole.springaisdk.client.OpenAiClient;
import io.github.manthanrangole.springaisdk.internal.HttpClientWrapper;

import java.time.Duration;

/**
 * Main entry point for the Spring AI SDK.
 *
 * <p>Create a single instance and use it to access all AI provider clients:</p>
 *
 * <pre>{@code
 * SpringAiClient client = new SpringAiClient("http://localhost:8080");
 *
 * // Chat with any model
 * String answer = client.anthropic().chat("What is Java?");
 * String answer = client.openai().chat("Explain microservices");
 * String answer = client.ollama().chat("What is Docker?");
 *
 * // Analyze an image
 * String vision = client.anthropic().analyzeImage(new File("diagram.png"));
 *
 * // Generate an image (returns hosted URL)
 * String url = client.openai().generateImage("a futuristic city at night");
 * }</pre>
 */
public class SpringAiClient {

    private final AnthropicClient anthropicClient;
    private final OpenAiClient openAiClient;
    private final OllamaClient ollamaClient;

    /**
     * Creates a new SpringAiClient with a default 30 second connection timeout.
     *
     * @param baseUrl the base URL of your deployed Spring AI server (e.g. "http://localhost:8080")
     */
    public SpringAiClient(String baseUrl) {
        this(baseUrl, Duration.ofSeconds(30));
    }

    /**
     * Creates a new SpringAiClient with a custom connection timeout.
     *
     * @param baseUrl the base URL of your deployed Spring AI server
     * @param timeout the connection timeout duration
     */
    public SpringAiClient(String baseUrl, Duration timeout) {
        HttpClientWrapper http = new HttpClientWrapper(baseUrl, timeout);
        this.anthropicClient = new AnthropicClient(http);
        this.openAiClient = new OpenAiClient(http);
        this.ollamaClient = new OllamaClient(http);
    }

    /**
     * Returns the Anthropic (Claude) client.
     */
    public AnthropicClient anthropic() {
        return anthropicClient;
    }

    /**
     * Returns the OpenAI (GPT-4o / DALL-E 3) client.
     */
    public OpenAiClient openai() {
        return openAiClient;
    }

    /**
     * Returns the Ollama (local models) client.
     */
    public OllamaClient ollama() {
        return ollamaClient;
    }
}
