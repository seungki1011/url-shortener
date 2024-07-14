package com.seungki.urlshortener.api.controller.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ApiErrorResponse {

    private final LocalDateTime errorTimestamp = LocalDateTime.now();
    private int status;
    private String code;
    private String error;
    private String message;
    private Map<String, Object> details;

    public ApiErrorResponse(ErrorCode errorCode, String message) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.error = errorCode.getMessage();
        this.message = message;
        this.details = new HashMap<>();
    }

    public void addDetail(String key, Object value) {
        this.details.put(key, value);
    }

}
