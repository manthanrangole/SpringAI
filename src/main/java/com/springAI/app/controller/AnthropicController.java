package com.springAI.app.controller;

import com.springAI.app.service.AnthropicService;
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
@RequestMapping("/api/anthropic")
public class AnthropicController {

    private static final Logger logger = LoggerFactory.getLogger(AnthropicController.class);

    @Autowired
    public AnthropicService anthropicService;

    @GetMapping("/{question}")
    public ResponseEntity<String> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/anthropic/{}", question);
        String response = anthropicService.getAnswer(question);
        return ResponseEntity.ok("Answer : " + response);
    }

    @PostMapping("/image")
    public ResponseEntity<String> getAnswerWithImage(
            @RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/anthropic/image");
        String response = anthropicService.getAnswerWithImage(file);
        logger.info("response : {}", response);
        return ResponseEntity.ok("Answer: " + response);
    }
}
