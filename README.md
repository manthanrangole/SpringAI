# Spring AI Application

A Spring Boot application that integrates with multiple AI providers using the Spring AI framework: OpenAI, Anthropic (Claude), and Ollama.

This project provides unified controllers and services for standard prompts, system-level instructions, structured data mapping, multimodal (vision) operations, and prompt-based image generation across all supported language models.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 4.0.6 |
| AI Abstraction | Spring AI 2.0.0-M4 |
| Language | Java 17 |
| Build Tool | Maven |
| Cloud AI Models | OpenAI (GPT-4o, DALL-E 3), Anthropic (Claude 3) |
| Local AI Models | Ollama (DeepSeek, LLaMA, etc.) |

---

## Prerequisites

- Java 17 or higher
- Maven
- API keys for OpenAI and Anthropic
- Ollama installed locally (for offline models)

---

## API Key Configuration

API keys and application parameters are configured in `src/main/resources/application.properties`. Ensure API keys are kept secure and excluded from version control tracking.

### 1. OpenAI
- Obtain an API key from the OpenAI Platform (platform.openai.com).
- Navigate to API Keys and generate a new secret key.
- Add the key to `application.properties` under `spring.ai.openai.api-key`.

### 2. Anthropic (Claude)
- Obtain an API key from the Anthropic Console (console.anthropic.com).
- Navigate to API Keys and generate a new key.
- Add the key to `application.properties` under `spring.ai.anthropic.api-key`.

### 3. Ollama (Local Models)
- Install the runtime from ollama.com.
- Pull a specific model locally using the terminal:
  ```bash
  ollama run deepseek-r1:latest
  ```
- Ollama runs locally at `http://localhost:11434` by default. Spring AI connects to it automatically.
- Define the model selection in `application.properties` under `spring.ai.ollama.chat.options.model`.

---

## Project Configuration Setup

Example `application.properties` configuration:

```properties
spring.application.name=springAi

spring.ai.openai.api-key=your-openai-key

spring.ai.anthropic.api-key=your-anthropic-key

spring.ai.ollama.chat.options.model=deepseek-r1:latest
```

---

## Running the Application

1. Open a terminal at the root directory of the project.
2. Compile and package the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the compiled JAR file:
   ```bash
   java -jar target/springAi.jar
   ```

The web server will initialize and bind to port **8080** by default.

---

## Project Structure

```
src/main/java/com/springAI/app/
├── SpringAiApplication.java          # Main entry point
├── controller/
│   ├── AnthropicController.java      # Anthropic REST endpoints
│   ├── OpenAiController.java         # OpenAI REST endpoints
│   └── OllamaController.java         # Ollama REST endpoints
├── service/
│   ├── AnthropicService.java         # Anthropic service interface
│   ├── OpenAiService.java            # OpenAI service interface
│   └── OllamaService.java            # Ollama service interface
└── serviceImpl/
    ├── AnthropicServiceImpl.java     # Anthropic business logic
    ├── OpenAiServiceImpl.java        # OpenAI business logic
    └── OllamaServiceImpl.java        # Ollama business logic
```

---

## Application Architecture

Each AI provider (`openAi`, `anthropic`, `ollama`) is equipped with its own dedicated controller and service. The service implementations heavily leverage `ChatClient` built directly from the provider-specific model to avoid Spring bean ambiguity.

### Service Capabilities

| Method | Description |
|---|---|
| `getAnswer(question)` | Standard chat prompt — returns a plain text response |
| `getAnswerWithImage(file)` | Multimodal vision — the model reads an uploaded image and describes it |
| `getAnswerWithSystemPrompt(question)` | Constrains the model persona and output format via a system instruction |
| `getMovieInfo(question)` | Structured output — maps model response directly to a Java record via `entity()` |
| `getStreamedAnswer(question)` | Reactive streaming — returns chunks via `Flux<String>` as they are generated |
| `generateImage(description)` | Generates an image with DALL-E 3 and returns the hosted URL |

---

## API Endpoints

The API layout is segmented by provider (`openAi`, `anthropic`, or `ollama`) before the desired action.

### Standard Text Prompts

Send a natural language question and receive a complete text response.

| Provider | Endpoint |
|---|---|
| OpenAI | `GET /api/openAi/{question}` |
| Anthropic | `GET /api/anthropic/{question}` |
| Ollama | `GET /api/ollama/{question}` |

```bash
curl -X GET http://localhost:8080/api/anthropic/what-is-spring-boot
```

---

### Multimodal Image Analysis (Vision)

Upload an image file. The model will read the visual contents and return a description or analysis.

| Provider | Endpoint |
|---|---|
| OpenAI | `POST /api/openAi/image` |
| Anthropic | `POST /api/anthropic/image` |
| Ollama | `POST /api/ollama/image` |

```bash
curl -X POST http://localhost:8080/api/anthropic/image \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/image.png"
```

---

### Prompt-Based Image Generation

Send a descriptive text prompt and receive a URL pointing to the AI-generated image asset (DALL-E 3).

| Provider | Endpoint |
|---|---|
| OpenAI | `GET /api/openAi/generate-image/{description}` |
| Anthropic | `GET /api/anthropic/generate-image/{description}` |
| Ollama | `GET /api/ollama/generate-image/{description}` |

```bash
curl -X GET "http://localhost:8080/api/openAi/generate-image/a-futuristic-cityscape-at-night"
```

> Note: Ollama does not natively support image generation and will return an informational message.

---

## Notes

- All API keys must be configured before starting the application or the context will fail to load.
- The `ImageModel` bean is provided by the `spring-ai-starter-model-openai` dependency and is shared across providers for image generation.
- The Spring Milestones repository is required in `pom.xml` to resolve Spring AI 2.0.0-M4 artifacts correctly.

---

## Standalone Java SDK

This project includes a **Standalone Java Client SDK** (`spring-ai-sdk`) that allows you to interact with this AI server from any Java application (Desktop, Android, or other backend services) without requiring the Spring Boot stack.

### Add Dependency (Maven)

```xml
<dependency>
    <groupId>io.github.manthanrangole</groupId>
    <artifactId>spring-ai-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Quick Usage Example

```java
import io.github.manthanrangole.springaisdk.SpringAiClient;

public class MyAiApp {

    // 1. Declare the client
    private SpringAiClient springAiClient;

    public void init() {
        // 2. Initialize the client
        springAiClient = new SpringAiClient("http://localhost:8080");

        // 3. Chat with any provider
        String answer = springAiClient.openai().chat("What is the capital of France?");
        System.out.println("Answer: " + answer);

        // 4. Generate images
        String imageUrl = springAiClient.anthropic().generateImage("A cat playing the piano");
        System.out.println("Image URL: " + imageUrl);
    }
}
```

For more details, see the [SDK README](./spring-ai-sdk/README.md).
