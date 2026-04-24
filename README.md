# SpringAi Application 🚀

Welcome to the **SpringAi Application**! This project demonstrates how to build a unified API using **Spring AI** to interact with multiple AI providers: **OpenAI**, **Anthropic (Claude)**, and **Ollama** (for local models). 

This guide will walk you through setting up your API keys, configuring the project, and getting it running locally.

---

## 📋 Prerequisites
- **Java 17** or higher ☕
- **Maven** 🛠️
- API keys for OpenAI and Anthropic (if you intend to use them).
- **Ollama** installed locally (if you intend to run local open-source models).

---

## 🔑 Step 1: Getting Your API Keys

You will need API keys to interact with cloud providers. Here is how to obtain them:

### 1. OpenAI API Key
1. Go to the [OpenAI Platform](https://platform.openai.com/).
2. Sign up or log into your account.
3. On the left sidebar, click on **API keys** (or click your profile icon in the top right -> **View API keys**).
4. Click **Create new secret key**.
5. Give it a name (e.g., `SpringAi Project`), and copy the key immediately **(you won't be able to see it again!)**.

### 2. Anthropic (Claude) API Key
1. Go to the [Anthropic Console](https://console.anthropic.com/).
2. Sign up or log into your account.
3. Click on your profile icon in the top right and select **API Keys**.
4. Click **Create Key**.
5. Give it a name (e.g., `SpringAi Project`) and copy the generated key.

---

## 🦙 Step 2: Setting Up Ollama (Local Models)

Ollama allows you to run large language models locally on your machine for free, without needing internet access or API credits!

1. Download and install Ollama from [ollama.com](https://ollama.com/download).
2. Once installed, open your terminal and pull a model. For example, to run the **DeepSeek r1** model:
   ```bash
   ollama run deepseek-r1:latest
   ```
   *(This will download the model to your computer. It might take a few minutes depending on your internet connection).*
3. Ollama runs seamlessly in the background on your local machine (usually at `http://localhost:11434`), and Spring AI will automatically connect to it!

---

## ⚙️ Step 3: Project Configuration

Now that you have your keys and local models sorted out, let's configure the application.

1. Navigate to `src/main/resources/application.properties`.
2. Add your keys and chosen Ollama model. It should look exactly like this:

```properties
spring.application.name=springAi

# Insert your actual OpenAI Key here
spring.ai.openai.api-key=sk-proj-YOUR-OPENAI-KEY-HERE

# Insert your actual Anthropic Key here
spring.ai.anthropic.api-key=sk-ant-YOUR-ANTHROPIC-KEY-HERE

# Set the name of the model you downloaded using Ollama
spring.ai.ollama.chat.options.model=deepseek-r1:latest
```

> **⚠️ WARNING**: NEVER push your API keys to GitHub! If you ever want to push your `.properties` file, make sure to replace them with placeholder text like `your-key-here`.

---

## 🚀 Step 4: Running the Application

You can launch the application in your terminal natively using Maven:

1. Open a terminal at the root of the project.
2. Clean and package the application:
   ```bash
   mvn clean install
   ```
3. Run the generated JAR file:
   ```bash
   java -jar target/springAi.jar
   ```

You will see the Spring Boot banner, and in a few seconds, the logs should indicate the server has started on port **8080**.

---

## 🧪 Step 5: Testing the APIs

Once your server is running (`http://localhost:8080`), you can hit the endpoints mapped into your controllers! 

### Anthropic Example endpoints:

**Basic Text Chat**:
```bash
curl -X GET http://localhost:8080/api/anthropic/what-is-java
```

**Vision Chat (With Images)**:
You can test the multimodal image parsing endpoint! Make sure you supply a file path to an image on your machine.
```bash
curl -X POST http://localhost:8080/api/anthropic/image \
  -H "Content-Type: multipart/form-data" \
  -F "file=@/path/to/your/image.png"
```

*(You can replicate these `curl` logic in Postman or interact with them natively for your OpenAi and Ollama controllers as well!)*
