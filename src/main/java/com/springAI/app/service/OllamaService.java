package com.springAI.app.service;

import org.springframework.web.multipart.MultipartFile;

public interface OllamaService {
    String getAnswer(String question);
    String getAnswerWithImage(MultipartFile file);
}
