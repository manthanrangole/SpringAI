package com.springAI.app.controller;

import com.springAI.app.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<String>> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/anthropic/{}", question);
        try {
            String response = anthropicService.getAnswer(question);
            return ResponseEntity.ok(ApiResponse.success("Anthropic", "text-chat", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Anthropic", "text-chat", e.getMessage()));
        }
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<String>> getAnswerWithImage(@RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/anthropic/image");
        try {
            String response = anthropicService.getAnswerWithImage(file);
            return ResponseEntity.ok(ApiResponse.success("Anthropic", "image-analysis", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Anthropic", "image-analysis", e.getMessage()));
        }
    }

    @GetMapping("/generate-image/{description}")
    public ResponseEntity<ApiResponse<String>> generateImage(@PathVariable String description) {
        logger.info("API Hit: GET /api/anthropic/generate-image/{}", description);
        try {
            String imageUrl = anthropicService.generateImage(description);
            return ResponseEntity.ok(ApiResponse.success("Anthropic", "image-generation", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Anthropic", "image-generation", e.getMessage()));
        }
    }
}