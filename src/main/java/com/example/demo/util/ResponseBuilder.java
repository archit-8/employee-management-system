package com.example.demo.util;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Generic API response builder for standardized responses
 */
@Getter
@Setter
public class ResponseBuilder {

    private int status;
    private String message;
    private Object data;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> errors;

    public ResponseBuilder() {
        this.timestamp = LocalDateTime.now();
        this.errors = new HashMap<>();
    }

    /**
     * Build success response
     */
    public static ResponseBuilder success(String message, Object data) {
        ResponseBuilder response = new ResponseBuilder();
        response.setStatus(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * Build success response with custom status code
     */
    public static ResponseBuilder success(int statusCode, String message, Object data) {
        ResponseBuilder response = new ResponseBuilder();
        response.setStatus(statusCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    /**
     * Build success response for created resource
     */
    public static ResponseBuilder created(String message, Object data) {
        return success(201, message, data);
    }

    /**
     * Build error response
     */
    public static ResponseBuilder error(int statusCode, String message) {
        ResponseBuilder response = new ResponseBuilder();
        response.setStatus(statusCode);
        response.setMessage(message);
        return response;
    }

    /**
     * Build validation error response
     */
    public static ResponseBuilder validationError(String message, Map<String, String> errors) {
        ResponseBuilder response = new ResponseBuilder();
        response.setStatus(400);
        response.setMessage(message);
        response.setErrors(errors);
        return response;
    }

    /**
     * Add error
     */
    public ResponseBuilder addError(String field, String errorMessage) {
        this.errors.put(field, errorMessage);
        return this;
    }

    /**
     * Set path
     */
    public ResponseBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    /**
     * Build response map
     */
    public Map<String, Object> build() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", this.status);
        response.put("message", this.message);
        response.put("data", this.data);
        response.put("timestamp", this.timestamp);
        response.put("path", this.path);

        if (!this.errors.isEmpty()) {
            response.put("errors", this.errors);
        }

        return response;
    }
}
