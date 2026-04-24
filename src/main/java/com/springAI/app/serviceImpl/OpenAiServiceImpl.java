package com.springAI.app.serviceImpl;

import com.springAI.app.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiServiceImpl implements OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiServiceImpl.class);

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Override
    public String getAnswer(String question) {
        logger.info("OpenAI Request received for question: {}", question);
        String response = openAiChatModel.call(question);
        logger.info("OpenAI Response generated successfully");
        return response;
    }
}
