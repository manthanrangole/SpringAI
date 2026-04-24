package com.springAI.app.serviceImpl;

import com.springAI.app.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaServiceImpl implements OllamaService {

    private static final Logger logger = LoggerFactory.getLogger(OllamaServiceImpl.class);

    @Autowired
    private OllamaChatModel ollamaChatModel;

    @Override
    public String getAnswer(String question) {
        logger.info("Ollama Request received for question: {}", question);
        String response = ollamaChatModel.call(question);
        logger.info("Ollama Response generated successfully");
        return response;
    }
}
