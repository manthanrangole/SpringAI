package com.springAI.app.controller;

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
    public ResponseEntity<String> getAnswer(@PathVariable String question) {
        logger.info("API Hit: GET /api/openAi/{}", question);
        String response = openAiService.getAnswer(question);
        return ResponseEntity.ok("Answer : " + response);
    }

    @PostMapping("/image")
    public ResponseEntity<String> getAnswerWithImage(@RequestParam("file") MultipartFile file) {
        logger.info("API Hit: POST /api/openAi/image");
        String response = openAiService.getAnswerWithImage(file);
        logger.info("response : {}", response);
        return ResponseEntity.ok("Answer: " + response);
    }

    @GetMapping("/generate-image/{description}")
    public ResponseEntity<String> generateImage(@PathVariable String description) {
        logger.info("API Hit: GET /api/openAi/generate-image/{}", description);
        String imageUrl = openAiService.generateImage(description);
        return ResponseEntity.ok("Image URL: " + imageUrl);
    }
}
