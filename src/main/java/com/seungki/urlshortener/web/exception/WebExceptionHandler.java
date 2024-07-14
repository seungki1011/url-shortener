package com.seungki.urlshortener.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("com.seungki.urlshortener.web.controller")
public class WebExceptionHandler {

    @ExceptionHandler(ShortcodeNotFoundException.class)
    public String handleShortcodeNotFoundException(ShortcodeNotFoundException ex, Model model) {
        log.info("[ShortcodeNotFoundException] message: {}", ex.getMessage());
        model.addAttribute("error", "Shortcode Not Found");
        return "error/404";
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public String handleUrlNotFoundException(UrlNotFoundException ex, Model model) {
        log.info("[UrlNotFoundException] message: {}", ex.getMessage());
        model.addAttribute("error", "URL Not Found");
        model.addAttribute("shortcode", ex.getShortcode());
        return "error/UrlNotFound";
    }

    @ExceptionHandler(ShortcodeGenerationException.class)
    public String handleShortcodeGenerationException(ShortcodeGenerationException ex, Model model) {
        log.info("[ShortcodeGenerationException] message: {}", ex.getMessage());
        model.addAttribute("error", "Shortcode Recreation Failed");
        return "error/500";
    }

}
