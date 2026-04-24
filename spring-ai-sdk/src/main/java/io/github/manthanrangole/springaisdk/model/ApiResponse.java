package io.github.manthanrangole.springaisdk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

/**
 * Wrapper response returned by all Spring AI SDK API calls.
 *
 * @param <T> the type of the data payload
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<T> {

    private boolean success;
    private String provider;
    private String action;
    private T data;
    private String error;
    private LocalDateTime timestamp;

    public ApiResponse() {}

    public boolean isSuccess() { return success; }
    public String getProvider() { return provider; }
    public String getAction() { return action; }
    public T getData() { return data; }
    public String getError() { return error; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setSuccess(boolean success) { this.success = success; }
    public void setProvider(String provider) { this.provider = provider; }
    public void setAction(String action) { this.action = action; }
    public void setData(T data) { this.data = data; }
    public void setError(String error) { this.error = error; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public String toString() {
        return "ApiResponse{success=" + success + ", provider='" + provider +
               "', action='" + action + "', data=" + data +
               ", error='" + error + "', timestamp=" + timestamp + "}";
    }
}
