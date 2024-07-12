package com.seungki.urlshortener.web.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
