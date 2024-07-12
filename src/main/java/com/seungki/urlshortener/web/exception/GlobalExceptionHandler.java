package com.seungki.urlshortener.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShortcodeNotFoundException.class)
    public String handleShortcodeNotFoundException(ShortcodeNotFoundException ex, Model model) {
        model.addAttribute("error", "404 Not Found");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public String handleUrlNotFoundException(UrlNotFoundException ex, Model model) {
        model.addAttribute("error", "URL Not Found");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("shortcode", ex.getShortcode());
        return "error/UrlNotFound";
    }
    
}
