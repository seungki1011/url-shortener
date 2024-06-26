package com.seungki.urlshortener.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShortCodeUtil {

    private static final int SHORTCODE_LENGTH = 7;

    public static String generateShortcode(String originalUrl) {
        byte[] hash = hashFunction(originalUrl);
        String base62Encoded = Base62.encode(hash);

        return base62Encoded.substring(0, SHORTCODE_LENGTH);
    }

    private static byte[] hashFunction(String originalUrl) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(originalUrl.getBytes());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
