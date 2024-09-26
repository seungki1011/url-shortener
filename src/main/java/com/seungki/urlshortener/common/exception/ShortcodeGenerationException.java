package com.seungki.urlshortener.common.exception;

public class ShortcodeGenerationException extends RuntimeException {
    public ShortcodeGenerationException() {
        super();
    }

    public ShortcodeGenerationException(String message) {
        super(message);
    }
}
