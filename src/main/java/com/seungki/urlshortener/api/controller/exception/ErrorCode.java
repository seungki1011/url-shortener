package com.seungki.urlshortener.api.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SHORTCODE_NOT_FOUND("E001", "Shortcode Not Found"),
    URL_NOT_FOUND("E002", "URL Not Found"),
    SHORTCODE_GENERATION_FAILED("E003", "Shortcode Generation Failed");

    private final String code;
    private final String message;
}
