package io.github.manthanrangole.springaisdk.internal;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.manthanrangole.springaisdk.exception.SpringAiSdkException;
import io.github.manthanrangole.springaisdk.model.ApiResponse;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.Duration;
import java.util.UUID;

/**
 * Internal HTTP client handling all communication with the Spring AI server.
 * Not intended for direct use — use {@link io.github.manthanrangole.springaisdk.SpringAiClient} instead.
 */
public class HttpClientWrapper {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpClientWrapper(String baseUrl, Duration timeout) {
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    /**
     * Sends a GET request and returns the parsed data string from ApiResponse.
     */
    public String get(String path) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .GET()
                    .timeout(Duration.ofSeconds(60))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseResponse(response);

        } catch (IOException | InterruptedException e) {
            throw new SpringAiSdkException("Failed to connect to Spring AI server at " + baseUrl, e);
        }
    }

    /**
     * Sends a multipart POST with a file and returns the parsed data string from ApiResponse.
     */
    public String postFile(String path, File file) {
        try {
            String boundary = UUID.randomUUID().toString();
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null) contentType = "application/octet-stream";

            // Build multipart body manually
            String partHeader = "--" + boundary + "\r\n" +
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n" +
                    "Content-Type: " + contentType + "\r\n\r\n";
            String closingBoundary = "\r\n--" + boundary + "--\r\n";

            byte[] headerBytes = partHeader.getBytes();
            byte[] closingBytes = closingBoundary.getBytes();
            byte[] body = new byte[headerBytes.length + fileBytes.length + closingBytes.length];
            System.arraycopy(headerBytes, 0, body, 0, headerBytes.length);
            System.arraycopy(fileBytes, 0, body, headerBytes.length, fileBytes.length);
            System.arraycopy(closingBytes, 0, body, headerBytes.length + fileBytes.length, closingBytes.length);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .timeout(Duration.ofSeconds(120))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return parseResponse(response);

        } catch (IOException | InterruptedException e) {
            throw new SpringAiSdkException("Failed to upload file to Spring AI server at " + baseUrl, e);
        }
    }

    private String parseResponse(HttpResponse<String> response) throws IOException {
        ApiResponse<String> apiResponse = objectMapper.readValue(
                response.body(),
                new TypeReference<ApiResponse<String>>() {}
        );

        if (!apiResponse.isSuccess()) {
            throw new SpringAiSdkException(
                    "[" + apiResponse.getProvider() + "/" + apiResponse.getAction() + "] " + apiResponse.getError(),
                    response.statusCode()
            );
        }

        return apiResponse.getData();
    }
}
