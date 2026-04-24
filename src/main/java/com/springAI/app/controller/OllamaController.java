package com.springAI.app.controller;

import com.springAI.app.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {

    private static final Logger logger = LoggerFactory.getLogger(OllamaController.class);

    @Autowired
    public OllamaService ollamaService;

    @GetMapping("/{question}")
    public ResponseEntity<String> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/ollama/{}", question);
        String response = ollamaService.getAnswer(question);
        return ResponseEntity.ok("Answer : " + response);
    }
}
