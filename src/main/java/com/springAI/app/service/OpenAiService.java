package com.springAI.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface OpenAiService {
    String getAnswer(String question);
    String getAnswerWithImage(MultipartFile file);
    String generateImage(String description);
}

