package com.springAI.app.controller;

import com.springAI.app.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
