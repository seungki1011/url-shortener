package com.seungki.urlshortener.common.exception;

public class ShortcodeNotFoundException extends RuntimeException {
    public ShortcodeNotFoundException() {
        super();
    }

    public ShortcodeNotFoundException(String shortcode) {
        super("[ShortcodeNotFoundException] shortcode: " + shortcode + " was not found!");
    }

}
