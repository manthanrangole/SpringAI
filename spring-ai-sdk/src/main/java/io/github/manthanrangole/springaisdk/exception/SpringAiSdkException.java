package io.github.manthanrangole.springaisdk.exception;

/**
 * Thrown when the Spring AI SDK server returns an error response
 * or when an HTTP communication failure occurs.
 */
public class SpringAiSdkException extends RuntimeException {

    private final int statusCode;

    public SpringAiSdkException(String message) {
        super(message);
        this.statusCode = -1;
    }

    public SpringAiSdkException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public SpringAiSdkException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = -1;
    }

    /**
     * Returns the HTTP status code from the server, or -1 if not applicable.
     */
    public int getStatusCode() {
        return statusCode;
    }
}
