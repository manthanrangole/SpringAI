package com.springAI.app.controller;

import com.springAI.app.dto.ApiResponse;
import com.springAI.app.service.OpenAiService;
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
@RequestMapping("/api/openAi")
public class OpenAiController {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiController.class);

    @Autowired
    public OpenAiService openAiService;

    @GetMapping("/{question}")
    public ResponseEntity<ApiResponse<String>> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/openAi/{}", question);
        try {
            String response = openAiService.getAnswer(question);
            return ResponseEntity.ok(ApiResponse.success("OpenAI", "text-chat", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("OpenAI", "text-chat", e.getMessage()));
        }
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<String>> getAnswerWithImage(@RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/openAi/image");
        try {
            String response = openAiService.getAnswerWithImage(file);
            return ResponseEntity.ok(ApiResponse.success("OpenAI", "image-analysis", response));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("OpenAI", "image-analysis", e.getMessage()));
        }
    }

    @GetMapping("/generate-image/{description}")
    public ResponseEntity<ApiResponse<String>> generateImage(@PathVariable String description) {
        logger.info("API Hit: GET /api/openAi/generate-image/{}", description);
        try {
            String imageUrl = openAiService.generateImage(description);
            return ResponseEntity.ok(ApiResponse.success("OpenAI", "image-generation", imageUrl));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.error("OpenAI", "image-generation", e.getMessage()));
        }
    }
}