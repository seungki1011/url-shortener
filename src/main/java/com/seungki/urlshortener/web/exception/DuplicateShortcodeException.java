package com.seungki.urlshortener.web.exception;

public class DuplicateShortcodeException extends RuntimeException {
    public DuplicateShortcodeException() {
        super();
    }

    public DuplicateShortcodeException(String shortcode) {
        super("[DuplicateShortcodeException] shortcode = " + shortcode);
    }

}
