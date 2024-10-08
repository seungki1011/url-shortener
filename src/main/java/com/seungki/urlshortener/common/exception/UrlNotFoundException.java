package com.seungki.urlshortener.common.exception;

public class UrlNotFoundException extends RuntimeException {

    private String shortcode;

    public UrlNotFoundException() {
        super();
    }

    public UrlNotFoundException(String shortcode) {
        super("[UrlNotFoundException] shortcode: " + shortcode + " was not found!");
        this.shortcode = shortcode;
    }

    public String getShortcode() {
        return shortcode;
    }
}
