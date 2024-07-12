package com.seungki.urlshortener.web.exception;

public class UrlNotFoundException extends RuntimeException {

    private String shortcode;

    public UrlNotFoundException() {
        super();
    }

    public UrlNotFoundException(String shortcode) {
        super("[UrlNotFoundException] shortcode = " + shortcode);
        this.shortcode = shortcode;
    }

    public String getShortcode() {
        return shortcode;
    }
}
