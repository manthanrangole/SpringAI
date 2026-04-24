package com.springAI.app.controller;

import com.springAI.app.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<String>> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/ollama/{}", question);
        try {
            String response = ollamaService.getAnswer(question);
            return ResponseEntity.ok(ApiResponse.success("Ollama", "text-chat", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Ollama", "text-chat", e.getMessage()));
        }
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<String>> getAnswerWithImage(@RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/ollama/image");
        try {
            String response = ollamaService.getAnswerWithImage(file);
            return ResponseEntity.ok(ApiResponse.success("Ollama", "image-analysis", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Ollama", "image-analysis", e.getMessage()));
        }
    }

    @GetMapping("/generate-image/{description}")
    public ResponseEntity<ApiResponse<String>> generateImage(@PathVariable String description) {
        logger.info("API Hit: GET /api/ollama/generate-image/{}", description);
        try {
            String response = ollamaService.generateImage(description);
            return ResponseEntity.ok(ApiResponse.success("Ollama", "image-generation", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("Ollama", "image-generation", e.getMessage()));
        }
    }
}