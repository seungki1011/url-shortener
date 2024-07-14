package com.seungki.urlshortener.api.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    VALIDATION_ERROR(400, "VALIDATION_ERROR", "입력값이 올바르지 않습니다. 검증에 실패하였습니다."),
    SHORTCODE_NOT_FOUND(404, "SHORTCODE_NOT_FOUND", "해당 숏코드를 찾을 수 없습니다."),
    URL_NOT_FOUND(404, "URL_NOT_FOUND", "해당 숏코드를 이용해 URL을 찾을 수 없습니다."),
    SHORTCODE_GENERATION_FAILED(500, "SHORTCODE_GENERATION_FAILED", "중복된 숏코드에 대한 숏코드 재생성에 실패했습니다. 관리자에 문의를 해주세요.");

    private final int status;
    private final String code;
    private final String message;
}
