package com.springAI.app.dto;

import java.time.LocalDateTime;

public class ApiResponse<T> {

    private boolean success;
    private String provider;
    private String action;
    private T data;
    private String error;
    private LocalDateTime timestamp;

    private ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    // Static factory for success responses
    public static <T> ApiResponse<T> success(String provider, String action, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = true;
        response.provider = provider;
        response.action = action;
        response.data = data;
        return response;
    }

    // Static factory for error responses
    public static <T> ApiResponse<T> error(String provider, String action, String errorMessage) {
        ApiResponse<T> response = new ApiResponse<>();
        response.success = false;
        response.provider = provider;
        response.action = action;
        response.error = errorMessage;
        return response;
    }

    public boolean isSuccess() { return success; }
    public String getProvider() { return provider; }
    public String getAction() { return action; }
    public T getData() { return data; }
    public String getError() { return error; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
