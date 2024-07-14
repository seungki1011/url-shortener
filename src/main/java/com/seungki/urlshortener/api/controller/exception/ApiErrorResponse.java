package com.seungki.urlshortener.api.controller.exception;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApiErrorResponse {

    private final LocalDateTime errorTimestamp = LocalDateTime.now();
    private int status;
    private String code;
    private String error;
    private String message;

    public ApiErrorResponse(int status, ErrorCode errorCode, String message) {
        this.status = status;
        this.code = errorCode.getCode();
        this.error = errorCode.getMessage();
        this.message = message;
    }
}
