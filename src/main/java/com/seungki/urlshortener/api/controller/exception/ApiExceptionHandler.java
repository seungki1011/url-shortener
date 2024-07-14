package com.seungki.urlshortener.api.controller.exception;

import com.seungki.urlshortener.web.exception.ShortcodeGenerationException;
import com.seungki.urlshortener.web.exception.ShortcodeNotFoundException;
import com.seungki.urlshortener.web.exception.UrlNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShortcodeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleShortcodeNotFoundException(ShortcodeNotFoundException ex) {
        log.info("[ShortcodeNotFoundException] message: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorCode.SHORTCODE_NOT_FOUND,
                ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        log.info("[UrlNotFoundException] message: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ErrorCode.URL_NOT_FOUND,
                ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ShortcodeGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleShortcodeGenerationException(ShortcodeGenerationException ex) {
        log.info("[ShortcodeGenerationException] message: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.SHORTCODE_GENERATION_FAILED, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
