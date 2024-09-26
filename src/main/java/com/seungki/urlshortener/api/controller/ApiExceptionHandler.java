package com.seungki.urlshortener.api.controller;

import static com.seungki.urlshortener.api.dto.ErrorCode.SHORTCODE_GENERATION_FAILED;
import static com.seungki.urlshortener.api.dto.ErrorCode.SHORTCODE_NOT_FOUND;
import static com.seungki.urlshortener.api.dto.ErrorCode.URL_NOT_FOUND;
import static com.seungki.urlshortener.api.dto.ErrorCode.VALIDATION_ERROR;

import com.seungki.urlshortener.api.dto.ApiErrorResponse;
import com.seungki.urlshortener.common.exception.ShortcodeGenerationException;
import com.seungki.urlshortener.common.exception.ShortcodeNotFoundException;
import com.seungki.urlshortener.common.exception.UrlNotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(ShortcodeNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleShortcodeNotFoundException(ShortcodeNotFoundException ex) {
        log.info("[ShortcodeNotFoundException] message: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ApiErrorResponse(SHORTCODE_NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        log.info("[UrlNotFoundException] message: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ApiErrorResponse(URL_NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ShortcodeGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleShortcodeGenerationException(ShortcodeGenerationException ex) {
        log.info("[ShortcodeGenerationException] message: {}", ex.getMessage());
        return new ResponseEntity<>(
                new ApiErrorResponse(SHORTCODE_GENERATION_FAILED, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("[ValidationException] message: {}", ex.getMessage());

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .toList();

        ApiErrorResponse errorResponse = new ApiErrorResponse(VALIDATION_ERROR, ex.getMessage());
        errorResponse.addDetail("errors", errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
