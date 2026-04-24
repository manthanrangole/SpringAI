# Spring AI Application

A Spring Boot application that integrates with multiple AI providers using the Spring AI framework: OpenAI, Anthropic (Claude), and Ollama. 

This project provides unified controllers and services for standard prompts, system-level instructions, structured data mapping, and multimodal (vision) operations across all supported language models.

## Prerequisites
- Java 17 or higher
- Maven
- API keys for OpenAI and Anthropic
- Ollama installed locally (for offline models)

## API Key Configuration

API keys and application parameters are configured in `src/main/resources/application.properties`. Ensure API keys are kept secure and excluded from version control tracking.

### 1. OpenAI
- Obtain an API key from the OpenAI Platform (platform.openai.com).
- Add the key to `application.properties` under `spring.ai.openai.api-key`.

### 2. Anthropic (Claude)
- Obtain an API key from the Anthropic Console (console.anthropic.com).
- Add the key to `application.properties` under `spring.ai.anthropic.api-key`.

### 3. Ollama (Local Models)
- Install the runtime from ollama.com.
- Pull a specific model locally using the terminal. Example: `ollama run deepseek-r1:latest`.
- Define the model selection in `application.properties` under `spring.ai.ollama.chat.options.model`.

## Project Configuration Setup

Example configuration block:

```properties
spring.application.name=springAi
spring.ai.openai.api-key=your-openai-key
spring.ai.anthropic.api-key=your-anthropic-key
spring.ai.ollama.chat.options.model=deepseek-r1:latest
```

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

The web server will initialize and bind to port 8080 by default.

## Application Architecture

Each AI provider (`openAi`, `anthropic`, `ollama`) is equipped with its own dedicated controller and service. The service implementations heavily leverage `ChatClient` to tap into advanced features of Spring AI.

### Supported Service Logic Capabilities:
- `getAnswer(String question)`: Basic chat response derivation.
- `getAnswerWithImage(MultipartFile file)`: Multimodal parsing of uploaded files. 
- `getAnswerWithSystemPrompt(String question)`: Constraining AI output format and persona natively. 
- `getMovieInfo(String question)`: Mapping raw AI output directly to Java POJOs/Records via `entity` mapping.
- `getStreamedAnswer(String question)`: Handling and exporting Flux reactive streams back to the client.
- `generateImage(String description)`: Prompt-based generation of image URLs utilizing integrated DALL-E configurations.

## API Endpoints

The API layout is segmented by provider string (`openAi`, `anthropic`, or `ollama`) preceding the desired action.

### Standard Text Prompts

Transmit a standard sequence to the desired system.

- OpenAI: `GET http://localhost:8080/api/openAi/{question}`
- Anthropic: `GET http://localhost:8080/api/anthropic/{question}`
- Ollama: `GET http://localhost:8080/api/ollama/{question}`

Example execution:
```bash
curl -X GET http://localhost:8080/api/anthropic/what-is-java
```

### Multimodal Image Analysis

Submit an image binary alongside internal execution logic. The model will parse the contents of the binary and return inferred relationships based on the system instructions.

- OpenAI: `POST http://localhost:8080/api/openAi/image`
- Anthropic: `POST http://localhost:8080/api/anthropic/image`
- Ollama: `POST http://localhost:8080/api/ollama/image`

Example execution:
```bash
curl -X POST http://localhost:8080/api/anthropic/image \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/image.png"
```

### Prompt-Based Image Generation

Send a descriptive prompt to generate an image directly. Returns the HTTP URL of the generated asset.

- OpenAI: `GET http://localhost:8080/api/openAi/generate-image/{description}`
- Anthropic: `GET http://localhost:8080/api/anthropic/generate-image/{description}`
- Ollama: `GET http://localhost:8080/api/ollama/generate-image/{description}`

Example execution:
```bash
curl -X GET http://localhost:8080/api/openAi/generate-image/a-futuristic-cityscape-at-night
```
