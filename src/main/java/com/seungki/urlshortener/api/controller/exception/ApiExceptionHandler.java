package com.seungki.urlshortener.api.controller.exception;

import com.seungki.urlshortener.web.exception.ShortcodeGenerationException;
import com.seungki.urlshortener.web.exception.ShortcodeNotFoundException;
import com.seungki.urlshortener.web.exception.UrlNotFoundException;
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
        ApiErrorResponse response = new ApiErrorResponse(ErrorCode.SHORTCODE_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUrlNotFoundException(UrlNotFoundException ex) {
        log.info("[UrlNotFoundException] message: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(ErrorCode.URL_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShortcodeGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleShortcodeGenerationException(ShortcodeGenerationException ex) {
        log.info("[ShortcodeGenerationException] message: {}", ex.getMessage());
        ApiErrorResponse response = new ApiErrorResponse(ErrorCode.SHORTCODE_GENERATION_FAILED, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.info("[ValidationException] message: {}", ex.getMessage());
        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .toList();
        ApiErrorResponse response = new ApiErrorResponse(ErrorCode.VALIDATION_ERROR, ex.getMessage());
        response.addDetail("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
