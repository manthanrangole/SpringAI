package com.springAI.app.controller;

import com.springAI.app.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/image")
    public ResponseEntity<String> getAnswerWithImage(@RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/ollama/image");
        String response = ollamaService.getAnswerWithImage(file);
        logger.info("response : {}", response);
        return ResponseEntity.ok("Answer: " + response);
    }
}
