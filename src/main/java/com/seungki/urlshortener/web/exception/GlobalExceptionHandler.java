package com.seungki.urlshortener.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortcodeNotFoundException.class)
    public String handleShortcodeNotFoundException(Model model) {
        model.addAttribute("error", "404 Not Found");
        return "error/404";
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public String handleUrlNotFoundException(UrlNotFoundException ex, Model model) {
        model.addAttribute("error", "URL Not Found");
        model.addAttribute("shortcode", ex.getShortcode());
        return "error/UrlNotFound";
    }

    @ExceptionHandler(ShortcodeGenerationException.class)
    public String handleShortcodeGenerationException(Model model) {
        model.addAttribute("error", "Shortcode Recreation Failed");
        return "error/500";
    }

}
