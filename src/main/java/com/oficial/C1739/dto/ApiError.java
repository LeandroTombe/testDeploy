package com.oficial.C1739.dto;

import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ApiError  implements Serializable {


    private String backendMessage;

    private String message;
    private String url;

    private String method;
    private LocalDateTime timestamp;

    private List<String> errors = new ArrayList<>();

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message, String backendMessage) {
        this.message = message;
        this.backendMessage = backendMessage;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String message, String backendMessage, String url, String method) {
        this.message = message;
        this.backendMessage = backendMessage;
        this.url = url;
        this.method = method;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBackendMessage() {
        return backendMessage;
    }

    public void setBackendMessage(String backendMessage) {
        this.backendMessage = backendMessage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }




}
