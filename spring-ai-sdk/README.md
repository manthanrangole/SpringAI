# Spring AI Java SDK

A lightweight, standalone Java client SDK for interacting with the Spring AI multi-model REST API. This SDK allows you to easily integrate OpenAI, Anthropic, and Ollama capabilities into any Java application without needing the full Spring Boot stack.

## Features

- **Unified Client**: Single entry point for all AI providers.
- **Provider Support**: OpenAI (GPT-4o, DALL-E 3), Anthropic (Claude 3), and Ollama (Local Models).
- **Vision Support**: Simple API for multimodal image analysis.
- **Image Generation**: Generate images and get hosted URLs.
- **Thread Safe**: Designed to be used as a singleton.
- **Minimal Dependencies**: Only depends on Jackson for JSON parsing.

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.github.manthanrangole</groupId>
    <artifactId>spring-ai-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

### 1. Initialize the Client
Point the client to your deployed Spring AI server:

```java
import io.github.manthanrangole.springaisdk.SpringAiClient;

SpringAiClient client = new SpringAiClient("http://localhost:8080");
```

### 2. Standard Chat
```java
// Chat with OpenAI
String openAiAnswer = client.openai().chat("What are the benefits of Spring AI?");

// Chat with Anthropic
String anthropicAnswer = client.anthropic().chat("Write a poem about Java.");

// Chat with local Ollama
String ollamaAnswer = client.ollama().chat("How do I install Docker?");
```

### 3. Image Analysis (Multimodal)
```java
File image = new File("path/to/image.png");
String description = client.openai().analyzeImage(image);
```

### 4. Image Generation
```java
String imageUrl = client.openai().generateImage("a futuristic library in space");
System.out.println("Image URL: " + imageUrl);
```

## Error Handling

The SDK throws `SpringAiSdkException` for connection issues or server-side errors.

```java
try {
    String response = client.openai().chat("...");
} catch (SpringAiSdkException e) {
    System.err.println("API Error: " + e.getMessage());
    System.err.println("Status Code: " + e.getStatusCode());
}
```

## License
MIT License. See [LICENSE](LICENSE) for more information.
